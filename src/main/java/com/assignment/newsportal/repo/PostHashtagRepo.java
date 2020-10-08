package com.assignment.newsportal.repo;

import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.PostHashtagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface PostHashtagRepo extends JpaRepository<PostHashtagMap, Long> {

    @Transactional
    @Modifying
    void deleteByPostId(Long postId);


    List<PostHashtagMap> findByHashtagId(Long hashtagId);

    @Query(value="Select p.postId from PostHashtagMap p where p.hashtagId=?1")
    List<Long> findPostIDS(Long hashtagId);

    @Transactional
    @Modifying
    @Query(value="Update PostHashtagMap p set p.hashtag=?2 , p.updatedBy=?3 where p.hashtagId=?1")
    void update(Long hashtagId, String name,Long userId);

    @Query(value = "Select p.postId from PostHashtagMap p where LOWER(p.hashtag) LIKE %?1% ")
    Set<Long> findByHashtag(String sval);
}
