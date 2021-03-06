package com.assignment.newsportal.repo;

import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {


    Optional<User> findByName(String name);

    Optional<User> findByUserId(Long userId);

    @Query("Select post from Post post where post.userId=?1 and post.isActive=true order by post.updatedAt desc")
    Page<Post> getUserPosts(Long userId,Pageable pageable);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);

    @Query("Select u from User u where u.isActive=true")
    Page<User> getAllActiveUserDetails(Pageable pageable);

    Optional<User> findByEmail(String email);
}
