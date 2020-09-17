package com.assignment.newsportal.repo;

import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

//    @Query(value = "Select u from User u where u.UserId = ?1")
//    User getUser(String userId);

//    @Query(value = "Select emp from User emp where emp.uniqueId=?1")
//    User getUserByUniqueId(String uniqueId);
//
//
//
//    @Query(value = "Select emp from User emp where emp.departmentId IN :list and emp.isActive=1")
//    List<User> getUsersOfCompany(List<Long> list);

    Optional<User> findByName(String name);
    Optional<User> findByUserId(Long userId);

    @Query("Select post from Post post where post.userId=?1 and post.isActive=true")
    Page<Post> getUserPosts(Long userId,Pageable pageable);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);

    @Query("Select u from User u where u.isActive=true")
    Page<User> getAllActiveUserDetails(Pageable pageable);
}
