package com.assignment.newsportal.controller;


import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.dto.request.*;
import com.assignment.newsportal.dto.response.JwtResponse;
import com.assignment.newsportal.dto.response.MessageResponse;
import com.assignment.newsportal.dto.update.UserUpdate;
import com.assignment.newsportal.entity.*;
//import com.assignment.newsportal.entity.Role;
//import com.assignment.newsportal.repo.RoleRepo;
import com.assignment.newsportal.repo.UserRepo;
import com.assignment.newsportal.repo.UserTopicRepo;
import com.assignment.newsportal.security.jwt.JwtUtils;
import com.assignment.newsportal.service.UserDetailsImpl;
import com.assignment.newsportal.service.UserServiceImpl;
//import com.assignment.newsportal.service.interfaces.UserService;
import com.assignment.newsportal.service.UserTopicService;
import com.assignment.newsportal.util.PostUtil;
import com.assignment.newsportal.util.UserTopicUtil;
import com.assignment.newsportal.util.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PostUtil postUtil;

    @Autowired
    UserTopicRepo userTopicRepo;

    @Autowired
    TopicDTO topicDTO;

    @Autowired
    UserTopicService userTopicService;

    @Autowired
    UserTopicUtil userTopicUtil;

//    @Autowired
//    RoleRepo roleRepo;
//    @Autowired
//    ModelMapper modelMapper;

    @Value("${example.app.adminName}")
    private String adminName;

    @Value("${example.app.adminPwd}")
    private String adminPwd;




    @PutMapping(value="/{userId}/assignmod")

    public ResponseEntity<?> assignMod(@Valid @RequestBody LoginRequest loginRequest, @PathVariable @NotNull Long userId){
        if((loginRequest.getName().equals(adminName))&&(loginRequest.getPassword().equals(adminPwd))){
            User user = userRepo.findByUserId(userId).orElse(null);
            if(user==null||!user.isActive())
                throw new UsernameNotFoundException("User Not Found with Id: "+ userId);
            if(user.getRole().equals(ERole.ROLE_MODERATOR)){
                return ResponseEntity.badRequest().body(new MessageResponse("User is already a moderator."));
            }
                user.setRole(ERole.ROLE_MODERATOR);
                return new ResponseEntity<>(userUtil.convertToDTO(user),HttpStatus.OK);
        }
         return new ResponseEntity<>(new MessageResponse("Error: Unauthorized."),HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value="/changePassword")
    public ResponseEntity<?> changePwd(@RequestBody @Valid PasswordChange passwordChange){
        if(!(passwordChange.getNewPwd().equals(passwordChange.getConfirm()))){
           return ResponseEntity.badRequest().body(new MessageResponse("Passwords do not match.")) ;
        }

        Long userId= jwtUtils.getSubject();
        userService.changePwd(userId, encoder.encode(passwordChange.getNewPwd()));
        return ResponseEntity.ok(new MessageResponse("Password changed successfully."));

    }


    @PostMapping(value="/signin")

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword()));


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userDetails= userService.loadUserById(userDetails.getuserId());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getuserId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    @PostMapping(value = "/signup")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO userDTO) {



        if(userDTO.getName().equals("")||userDTO.getEmail().equals("")||userDTO.getPwd().equals("")||userDTO.getRole().equals("")){
            return ResponseEntity.badRequest().body(new MessageResponse("All details are compulsory."));
        }
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use."));
//            throw new DuplicateDataException("Email Already in Use!");
        }

        User user = new User(userDTO.getName(),
                userDTO.getEmail(),
                encoder.encode(userDTO.getPwd()));


        String strRole=userDTO.getRole();


        if (strRole == null) {

                    throw new RuntimeException("Error: Please enter role.");

        } else {
//
                switch (strRole) {

                    case "moderator":
//
                        user.setRole(ERole.ROLE_MODERATOR);
                        break;
                    default:

                        user.setRole(ERole.ROLE_CONSUMER);
                }

        }
//
        user = userService.register(user);
        return new ResponseEntity<>(userUtil.convertToDTO(user), HttpStatus.OK);

    }



    @DeleteMapping(value="/close")

    public ResponseEntity<?> closeAccount(/*@RequestHeader("Authorization") String token*/) {

//        String jwt = token.substring(7);
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
        Long userId=jwtUtils.getSubject();
        userService.remove(userId);

        return ResponseEntity.ok(new MessageResponse("Account deleted successfully."));
    }

    @GetMapping(value="/posts")

    public ResponseEntity<?> getUserPosts(/*@RequestHeader("Authorization") String token,*/ Pageable pageable){

//        String jwt = token.substring(7);
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
        Long userId=jwtUtils.getSubject();
        //userService.getUserPosts(userId,pageable);
        Page<Post> postList = userService.getUserPosts(userId,pageable);
        List<PostDTO> postDTOS = postList.stream().map(post -> postUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);

    }

    @GetMapping(value="/all")
    public ResponseEntity<?> getAllUserDetails(Pageable pageable){

//        String jwt = token.substring(7, token.length());
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
//        userService.getUserPosts(userId,pageable);
        Page<User> userList = userService.getAllUserDetails(pageable);
        List<UserDTO> userDTOS = userList.stream().map(post -> userUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);

    }

    @PutMapping(value="/update")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateUserDetails(@Valid @RequestBody UserUpdate userUpdate/* ,@RequestHeader("Authorization") String token*/){

        if (userRepo.existsByEmail(userUpdate.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use."));
//            throw new DuplicateDataException("Email Already in Use!");
        }
//        String jwt = token.substring(7);
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
        Long userId= jwtUtils.getSubject();
        User user=userService.updateUserDetails(userId,userUpdate);

        return new ResponseEntity<>(userUtil.convertToDTO(user),HttpStatus.OK);




    }

    @PutMapping(value="/{topicId}")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<?> addTopics(@PathVariable @NotNull Long topicId/*,@RequestHeader("Authorization") String token*/){
//        String jwt = token.substring(7);
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
        Long userId= jwtUtils.getSubject();
        UserTopicMap userTopic=userTopicService.add(userId,topicId);
        TopicDTO topicDTO= new TopicDTO(topicId,userTopic.getTopic());

        return new ResponseEntity<>(topicDTO,HttpStatus.OK);
    }

//    @PostMapping(value="/topics")
//    @PreAuthorize("hasRole('CONSUMER')")
//    public ResponseEntity<?> addMulTopics(@RequestHeader("Authorization") String token, @RequestBody @Valid TopicsRequest topicsRequest, Pageable pageable){
//        String jwt= token.substring(7);
//        Long userId=Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
//
//        List<String> topics= topicsRequest.getFollow();
//        List<PostDTO> postDTOS = postList.stream().map(post -> postUtil.convertToDTO(post)).collect(Collectors.toList());
//        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
//
//    }



    @GetMapping(value="/topics")
    @PreAuthorize("hasRole('CONSUMER')")
    ResponseEntity<?> getTopicsFollowed(/*@RequestHeader("Authorization") String token,*/ Pageable pageable){

//        String jwt = token.substring(7);
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
        Long userId= jwtUtils.getSubject();
        Page<UserTopicMap> list =  userTopicService.getTopics(userId,pageable);
        List<TopicDTO> topicDTOS = list.stream().map(ele -> userTopicUtil.convertToDTO(ele)).collect(Collectors.toList());
        return new ResponseEntity<>(topicDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value="/topic/{topicId}")
    @PreAuthorize("hasRole('CONSUMER')")
    ResponseEntity<?> removeTopicFollowed(/*@RequestHeader("Authorization") String token,*/@PathVariable @NotNull Long topicId){

//        String jwt = token.substring(7);
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
        Long userId= jwtUtils.getSubject();
        userTopicService.remove(userId, topicId);

        return ResponseEntity.ok(new MessageResponse("Topic unfollowed."));
    }




}
