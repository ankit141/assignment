package com.assignment.newsportal.service;

import com.assignment.newsportal.Exception.MissingDetailException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.update.UserUpdate;
import com.assignment.newsportal.entity.*;
import com.assignment.newsportal.repo.UserRepo;
//import com.assignment.newsportal.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;




    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepo.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + name));

        return UserDetailsImpl.build(user);
    }

    public UserDetailsImpl loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepo.findByUserId(userId).orElse(null);
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with Id: " + userId));
         if(user==null||!user.isActive())
             throw new UsernameNotFoundException("User Not Found with Id: "+ userId);
        return UserDetailsImpl.build(user);
    }



    public User register(User user) {

        user.setActive(true);
        return userRepo.save(user);

    }

    public void remove(Long userId){
        User user = userRepo.findByUserId(userId).orElse(null);
        if(user==null||!user.isActive())
           throw new NotFoundException("User Not Found with Id: " + userId);
        user.setActive(false);
        userRepo.save(user);

    }

    public Page<Post> getUserPosts(Long userId, Pageable pageable){
        return userRepo.getUserPosts(userId, pageable);

    }

    public Page<User> getAllUserDetails(Pageable pageable) {
      return userRepo.getAllActiveUserDetails(pageable)  ;

    }

    public User updateUserDetails(Long userId, UserUpdate userUpdate) {
        if(userUpdate.getName().equals("")||userUpdate.getEmail().equals(""))
            throw new MissingDetailException("All Details are compulsory");
        User user=userRepo.findByUserId(userId).orElse(null);
        if(user==null||!user.isActive())
            throw new NotFoundException("User with id "+userId+" does not exist");
        user.setName(userUpdate.getName());
        user.setEmail(userUpdate.getEmail());
        return userRepo.save(user);
    }

    public void changePwd(Long userId,String password) {
        User user= userRepo.findByUserId(userId).orElse(null);
        if(user==null||!user.isActive())
            throw new NotFoundException("User with id "+userId+" does not exist");

        user.setPwd(password);
        userRepo.save(user);

    }
}
