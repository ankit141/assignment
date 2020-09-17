package com.assignment.newsportal.repo;

import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepo extends JpaRepository<Topic, Long> {
    
    @Query(value = "Select t from Topic t where t.isActive=true")
    Page<Topic> getActiveTopics(Pageable pageable);

    Optional<Topic> findByTopicId(Long topicId);

    Optional<Topic> findByTopic(String topic);

    @Query(value = "Select t.isActive from Topic t where t.topic=?1")
    boolean existsByTopic(String topic);


    @Query("Select h from Hashtag h where h.topicId=?1 and h.isActive=true")
    Page<Hashtag> getTopicHashtags(Long topicId, Pageable pageable);
}
