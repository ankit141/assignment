package com.assignment.newsportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="user_topic_map")
public class UserTopicMap {
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name="user_id", referencedColumnName="user_id"),
//            @JoinColumn(name="name", referencedColumnName="name")
//    })
//    private Users user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name="topicId", referencedColumnName="topicId"),
//            @JoinColumn(name="topic", referencedColumnName="topic")
//    })
//    private Topics topic;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="name")
    private String name;

    @Column(name="topic_id")
    private Long topicId;

    @Column(name="topic")
    private String topic;

    @Column(name="is_active")
    private Boolean isActive;

    public UserTopicMap() {
    }

    public UserTopicMap(Long id, Long userId, String name, Long topicId, String topic, Boolean isActive) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.topicId = topicId;
        this.topic = topic;
        this.isActive = isActive;
    }

    public UserTopicMap(Long userId, String name, Long topicId, String topic, Boolean isActive) {
        this.userId=userId;
        this.name=name;
        this.topicId=topicId;
        this.topic=topic;
        this.isActive=isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
