package com.assignment.newsportal.controller;


import com.assignment.newsportal.Exception.*;
import com.assignment.newsportal.dto.request.*;
import com.assignment.newsportal.dto.response.JwtResponse;
import com.assignment.newsportal.dto.response.MessageResponse;
import com.assignment.newsportal.dto.update.UserUpdate;
import com.assignment.newsportal.entity.*;
import com.assignment.newsportal.repo.UserRepo;
import com.assignment.newsportal.repo.UserTopicRepo;
import com.assignment.newsportal.security.jwt.JwtUtils;
import com.assignment.newsportal.service.AuthService;
import com.assignment.newsportal.service.UserDetailsImpl;
import com.assignment.newsportal.service.UserServiceImpl;
import com.assignment.newsportal.service.UserTopicService;
import com.assignment.newsportal.util.PostUtil;
import com.assignment.newsportal.util.UserTopicUtil;
import com.assignment.newsportal.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    UserDTO userDTO;

    @Autowired
    UserTopicService userTopicService;

    @Autowired
      UserTopicUtil userTopicUtil;

    @Autowired
    AuthService authService;


    @Value("${example.app.adminMail}")
    private String adminMail;

    @Value("${example.app.adminPwd}")
    private String adminPwd;



    @PutMapping(value="/{userId}/assignmod")
    public ResponseEntity<?> assignMod(@Valid @RequestBody LoginRequest loginRequest, @PathVariable @NotNull Long userId){
        if((loginRequest.getEmail().equals(adminMail))&&(loginRequest.getPassword().equals(adminPwd))){
            User user = userRepo.findByUserId(userId).orElse(null);
            if(user==null||!user.isActive())
                throw new NotFoundException("User Not Found with Id: "+ userId);
            if(user.getRole().equals(ERole.ROLE_MODERATOR)){
                throw new DuplicateDataException("User is already a moderator.");
            }
                userDTO=userService.assignMod(user);
                return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }
         throw new AccessDeniedException("Invalid Credentials");
    }

    @PutMapping(value="/changePassword")
    public ResponseEntity<?> changePwd(@RequestBody @Valid PasswordChange passwordChange){
        if(!(passwordChange.getNewPwd().equals(passwordChange.getConfirm()))){
           throw new MismatchException("Passwords do not match.") ;
        }

        Long userId= jwtUtils.getSubject();
        userService.changePwd(userId, encoder.encode(passwordChange.getNewPwd()));
        return new ResponseEntity<>(new MessageResponse("Password changed successfully."),HttpStatus.OK);

    }


    @PostMapping(value="/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new JwtResponse(jwt,
                userDetails.getuserId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles),HttpStatus.OK);
    }


    @PostMapping(value = "/signup")
    public ResponseEntity<?> register(@RequestBody @Valid UserTotalDTO userTotalDTO) {

        if (userRepo.existsByEmail(userTotalDTO.getEmail())) {
            throw new DuplicateDataException("Email Already in Use");
        }

        User user = new User(userTotalDTO.getName(),
                userTotalDTO.getEmail(),
                encoder.encode(userTotalDTO.getPwd()));

        user.setRole(ERole.ROLE_CONSUMER);

        userDTO = userService.register(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }



    @DeleteMapping(value="/close")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> closeAccount() {

        Long userId=jwtUtils.getSubject();
        userService.remove(userId);

        return ResponseEntity.ok(new MessageResponse("Account deleted successfully."));
    }

    @GetMapping(value="/posts")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getMyPosts( @RequestParam(value = "page",defaultValue = "0")int page,
                                         @RequestParam(value = "size",defaultValue = "10")int size){

        Long userId=jwtUtils.getSubject();

        Pageable pageable= PageRequest.of(page,size);
        Page<Post> postList = userService.getUserPosts(userId,pageable);
        if(postList.isEmpty()){
            if(postList.isEmpty()){
                if(page==0)
                    throw new NotFoundException("No posts present");
                throw new NotFoundException("No results in page "+page);
            }
        }

        List<PostDTO> postDTOS = postList.stream().map(post -> postUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);

    }


    @GetMapping(value="/profile")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getUserDetails(){

        Long userId=jwtUtils.getSubject();
        userDTO= userService.getUserDetails(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    @GetMapping(value="/all")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getAllUserDetails(@RequestParam(value = "page",defaultValue = "0")int page,
                                               @RequestParam(value = "size",defaultValue = "10")int size){


        Pageable pageable= PageRequest.of(page,size);
        Page<User> userList = userService.getAllUserDetails(pageable);

            if(userList.isEmpty()){
                if(page==0)
                    throw new NotFoundException("No users present");
                throw new NotFoundException("No results in page "+page);
            }

        List<UserDTO> userDTOS = userList.stream().map(post -> userUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);

    }

    @PutMapping(value="/update")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateUserDetails(@Valid @RequestBody UserUpdate userUpdate){

        if (userRepo.existsByEmail(userUpdate.getEmail())) {
            throw new DuplicateDataException("Email Already in Use");
        }
        Long userId= jwtUtils.getSubject();
        userDTO=userService.updateUserDetails(userId,userUpdate);

        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }



    @PostMapping(value="/topics")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> addMulTopics(@RequestBody @Valid TopicsRequest topicsRequest){
        Long userId= jwtUtils.getSubject();
        Set<String> topics= topicsRequest.getFollow();
        if(topics==null||topics.isEmpty())
            throw new InvalidRequestException("No topics added");
        List<TopicDTO>topicDTOS= new ArrayList<>();

        for(String t: topics){
            UserTopicMap userTopic=userTopicService.add(userId,t);

            if(userTopic!=null) {
                TopicDTO topicDTO= new TopicDTO(userTopic.getTopicId(),t);

                topicDTOS.add(topicDTO);
            }
        }
        return new ResponseEntity<>(topicDTOS, HttpStatus.OK);

    }
    @GetMapping(value="/topics")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getTopicsFollowed (@RequestParam(value = "page",defaultValue = "0")int page,
                                                @RequestParam(value = "size",defaultValue = "10")int size){

        Long userId= jwtUtils.getSubject();
        Pageable pageable= PageRequest.of(page, size);
        Page<UserTopicMap> list =  userTopicService.getTopics(userId,pageable);

        if(list.isEmpty()){
            if(page==0)
                throw new NotFoundException("No topics followed.");
            throw new NotFoundException("No results in page "+page);
        }
        List<TopicDTO> topicDTOS = list.stream().map(ele -> userTopicUtil.convertToDTO(ele)).collect(Collectors.toList());
        return new ResponseEntity<>(topicDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value="/topic/{topicId}")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> removeTopicFollowed(@PathVariable @NotNull Long topicId){

        Long userId= jwtUtils.getSubject();
        userTopicService.remove(userId, topicId);

        return new ResponseEntity<>(new MessageResponse("Topic unfollowed."),HttpStatus.OK);
    }

    @GetMapping(value="/{userId}/topics")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getUserTopics(@PathVariable @NotNull Long userId,@RequestParam(value = "page",defaultValue = "0")int page,
                                           @RequestParam(value = "size",defaultValue = "10")int size){

        User user=userRepo.findByUserId(userId).orElse(null);
        if(user==null||user.isActive()==false)
            throw new NotFoundException("User does not exist");

        Pageable pageable= PageRequest.of(page,size);
        Page<UserTopicMap> list =  userTopicService.getTopics(userId,pageable);
        if(list.isEmpty()){
            if(page==0)
                throw new NotFoundException("No topics followed.");
            throw new NotFoundException("No results in page "+page);
        }
        List<TopicDTO> topicDTOS = list.stream().map(ele -> userTopicUtil.convertToDTO(ele)).collect(Collectors.toList());
        return new ResponseEntity<>(topicDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/{userId}/posts")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getUserPosts(@PathVariable @NotNull Long userId,@RequestParam(value = "page",defaultValue = "0")int page,
                                          @RequestParam(value = "size",defaultValue = "10")int size){


        User user=userRepo.findByUserId(userId).orElse(null);
        if(user==null||user.isActive()==false)
            throw new NotFoundException("User does not exist");

        Pageable pageable= PageRequest.of(page,size);
        Page<Post> postList = userService.getUserPosts(userId,pageable);
        if(postList.isEmpty()){
            if(page==0)
                throw new NotFoundException("No posts present.");
            throw new NotFoundException("No results in page "+page);
        }
        List<PostDTO> postDTOS = postList.stream().map(post -> postUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);

    }


}
