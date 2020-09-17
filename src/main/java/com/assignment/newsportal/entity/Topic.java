package com.assignment.newsportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="topics")
public class Topic extends Common{
    @Id
    @Column(name="topic_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long topicId;

    @Column(name="topic")
    private String topic;


      private Boolean isActive;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topic",cascade = CascadeType.ALL)
//    private List<Hashtags> hashtags;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topic",cascade = CascadeType.ALL)
//    private List<PostHashtagMap> users;


    public Topic() {
    }

    public Topic(Long topicId, String topic, Boolean isActive) {
        this.topicId = topicId;
        this.topic = topic;
        this.isActive = isActive;
    }

    public Topic(Long topicId, String topic) {
        this.topicId = topicId;
        this.topic = topic;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
