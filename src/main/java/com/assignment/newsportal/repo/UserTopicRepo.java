package com.assignment.newsportal.repo;

import com.assignment.newsportal.entity.User;
import com.assignment.newsportal.entity.UserTopicMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserTopicRepo extends JpaRepository<UserTopicMap, Long> {


    @Query(value = "Select u.isActive from UserTopicMap u where u.userId=?1 and u.topicId=?2")
    boolean existsByTopicId(Long userId, Long topicId);


    @Query(value = "Select u from UserTopicMap u where u.isActive=true and u.userId=?1")
    Page<UserTopicMap> getTopics(Long userId, Pageable pageable);


    @Query(value = "Select u from UserTopicMap u where u.userId=?1 and u.topicId=?2")
    Optional<UserTopicMap> find(Long userId, Long topicId);
}
