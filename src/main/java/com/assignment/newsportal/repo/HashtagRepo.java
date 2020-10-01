package com.assignment.newsportal.repo;

import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepo extends JpaRepository<Hashtag, Long> {

    @Query(value = "Select h from Hashtag h where h.hashtag=?1 and h.isActive=true")
    Optional<Hashtag> findByHashtag(String hashtag);


    @Query(value = "Select h from Hashtag h where h.isActive=true")
    Page<Hashtag> getActiveHashtags(Pageable pageable);

    Optional<Hashtag> findByHashtagId(Long hashtagId);

//
//    @Query(value = "Select h from Hashtag h where h.hashtag=?1 and h.isActive=true")
//    Hashtag exist(String hashtag);
}
