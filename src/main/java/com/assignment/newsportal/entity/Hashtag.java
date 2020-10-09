package com.assignment.newsportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="hashtags")
public class Hashtag extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="hashtag_id")
    private Long hashtagId;



    @Column(name="topic_id")
    private Long topicId;

    @Column(name="hashtag")
    private String hashtag;

    @Column(name="is_active")
    private Boolean isActive;


    public Hashtag() {
    }



    public Hashtag(Long topicId, String hashtag, Boolean isActive) {

        this.topicId = topicId;
        this.hashtag = hashtag;
        this.isActive = isActive;
    }



    public Hashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
