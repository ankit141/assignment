package com.assignment.newsportal.repo;

import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HashtagRepo extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByHashtag(String hashtag);


    @Query(value = "Select h from Hashtag h where h.isActive=true")
    Page<Hashtag> getActiveHashtags(Pageable pageable);

    Optional<Hashtag> findByHashtagId(Long hashtagId);


    @Query(value = "Select h.isActive from Hashtag h where h.hashtag=?1")
    boolean existsByHashtag(String hashtag);
}
