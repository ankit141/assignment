package com.assignment.newsportal.service;

import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.UserDTO;
import com.assignment.newsportal.dto.update.UserUpdate;
import com.assignment.newsportal.entity.*;
import com.assignment.newsportal.repo.UserRepo;
import com.assignment.newsportal.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserServiceImpl  {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserUtil userUtil;

    @Autowired
    AuthService authService;

    public UserDTO register(User user) {

        user.setActive(true);
        user.setCreatedBy(0L);
        user.setUpdatedBy(0L);
        userRepo.save(user);
        return userUtil.convertToDTO(user);


    }

    @CacheEvict(cacheNames = "user",key="#userId")
    public void remove(Long userId){
        User user = userRepo.findByUserId(userId).orElse(null);
        if(user==null||!user.isActive())
           throw new NotFoundException("User Not Found with Id: " + userId);
        user.setActive(false);
        user.setUpdatedBy(0L);
        userRepo.save(user);
        authService.removeAuthcache(userId);

    }

    public Page<Post> getUserPosts(Long userId, Pageable pageable){
        return userRepo.getUserPosts(userId, pageable);

    }

    public Page<User> getAllUserDetails(Pageable pageable) {
      return userRepo.getAllActiveUserDetails(pageable)  ;

    }

    @CachePut(cacheNames = "user",key="#userId")
    public UserDTO updateUserDetails(Long userId, UserUpdate userUpdate) {

        User user=userRepo.findByUserId(userId).orElse(null);
        if(user==null||!user.isActive())
            throw new NotFoundException("User with id "+userId+" does not exist");
        user.setName(userUpdate.getName());
        user.setEmail(userUpdate.getEmail());
        user.setUpdatedBy(0L);
        userRepo.save(user);
        authService.update(user);
        return userUtil.convertToDTO(user);
    }


    public void changePwd(Long userId,String password) {
        User user= userRepo.findByUserId(userId).orElse(null);
        if(user==null||!user.isActive())
            throw new NotFoundException("User with id "+userId+" does not exist");

        user.setPwd(password);
        user.setUpdatedBy(0L);
        userRepo.save(user);
        authService.changePwd(user);

    }

    @Cacheable(cacheNames = "user",key="#userId")
    public UserDTO getUserDetails(Long userId) {
        User user=userRepo.findByUserId(userId).orElse(null);
        if(user==null||!user.isActive())
            throw new NotFoundException("User with id "+userId+" does not exist");
        return userUtil.convertToDTO(user);

    }

    @CachePut(cacheNames = "user",key="#user.userId")
    public UserDTO assignMod(User user) {
        user.setRole(ERole.ROLE_MODERATOR);
        user.setUpdatedBy(-1L);
        userRepo.save(user);
        authService.update(user);
        return userUtil.convertToDTO(user);

    }
}
