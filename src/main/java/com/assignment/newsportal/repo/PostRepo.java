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
public interface PostRepo extends JpaRepository<Post, Long> {

    @Query(value = "Select p from Post p where p.isActive=true order by p.updatedAt desc")
    Page<Post> getActivePosts(Pageable pageable);

    Optional<Post> findByPostId(Long postId);
    @Query(value = "Select p.userId from Post p where p.isActive=true and p.postId=?1")
    Optional<Long> findUserIdOfPost(Long postId);

//    @Query(value = "Select p from Post p where (p.title=?1 or ?1= any (p.hashtags)) and p.isActive=true order by p.updatedAt desc")
//    Page<Post> getSearchedPosts(String val,Pageable pageable);

    @Query(value = "Select p from Post p where ?1 member of p.hashtags and p.isActive=true order by p.updatedAt desc")
    Page<Post> findByHashtag(String searchVal, Pageable pageable);

    @Query(value = "Select p from Post p where (p.title=?1 and p.isActive=true) order by p.updatedAt desc")
    Page<Post> findByTitle(String searchVal, Pageable pageable);
}
