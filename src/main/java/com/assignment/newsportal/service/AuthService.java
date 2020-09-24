package com.assignment.newsportal.service;

import com.assignment.newsportal.entity.User;
import com.assignment.newsportal.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;



    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElse(null);
        if(user==null||!user.isActive())
            throw new UsernameNotFoundException("User Not Found.");

        return UserDetailsImpl.build(user);
    }

    @Transactional
    @Cacheable(cacheNames = "Authenticator", key = "#userId")
    public UserDetailsImpl loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepo.findByUserId(userId).orElse(null);
        if(user==null||!user.isActive())
            throw new UsernameNotFoundException("User Not Found with Id: "+ userId);
        return UserDetailsImpl.build(user);
    }

    @CacheEvict(cacheNames = "Authenticator", key = "#userId")
    public void removeAuthcache(Long userId) {
    }

    @CachePut(cacheNames = "Authenticator", key="#user.userId")
    public UserDetailsImpl changePwd(User user){
        return UserDetailsImpl.build(user);

    }

    @CachePut(cacheNames = "Authenticator", key="#user.userId")
    public UserDetailsImpl update(User user){
        return UserDetailsImpl.build(user);
    }

}
