package com.assignment.newsportal.repo;

import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.VoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteStatusRepo extends JpaRepository<VoteStatus, Long> {

    @Query(value="Select v from VoteStatus v where v.userId=?1 and v.postId=?2")
    Optional<VoteStatus> find(Long userId, Long postId);
}
