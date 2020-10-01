package com.assignment.newsportal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="post_hashtag_map")
public class PostHashtagMap extends Common{


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="post_id")
    private Long postId;

    @Column(name="hashtag_id")
    private Long hashtagId;

    @Column(name="hashtag")
    private String hashtag;

    @Column(name="topic_id")
    private Long topicId;

    @Column(name="user_id")
    private Long userId;

//    @Column(name="is_active")
//    private Boolean isActive;

//    public PostHashtagMap(Long id, Long postId, Long hashtagId, String hashtag, Long topic_id, Long userId, Boolean isActive) {
//        this.id = id;
//        this.postId = postId;
//        this.hashtagId = hashtagId;
//        this.hashtag = hashtag;
//        this.topic_id = topic_id;
//        this.userId = userId;
//        this.isActive = isActive;
//    }

    public PostHashtagMap() {
    }

    public PostHashtagMap(Long postId, Long hashtagId, String hashtag, Long topicId, Long userId) {
        this.postId=postId;
        this.hashtagId=hashtagId;
        this.hashtag=hashtag;
        this.topicId=topicId;
        this.userId=userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(Long hashtagId) {
        this.hashtagId = hashtagId;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

//    public Boolean getActive() {
//        return isActive;
//    }
//
//    public void setActive(Boolean active) {
//        isActive = active;
//    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
}
