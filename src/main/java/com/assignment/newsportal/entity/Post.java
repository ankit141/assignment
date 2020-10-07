package com.assignment.newsportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="posts")
public class Post extends Common{
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name="user_id",referencedColumnName = "user_id")
//    })
//    private Users user;
    @Column(name="user_id")
    private Long userId;

    @Column(name="title")
    private String title;

    @Column(name="hashtags")
    @ElementCollection
    private Set<String> hashtags;

    @Column(name="body")
    private String body;

    @Column(name="upvotes")
    private Long upvotes;

    @Column(name="downvotes")
    private Long downvotes;

    @Column(name="isDraft")
    private Boolean isDraft;

    @Column(name="is_active")
    private Boolean isActive;


    public Post() {
    }

    public Post(String title, String body, Set<String> hashtags, long upvotes, long downvotes, boolean isDraft,boolean isActive,Long userId) {
        this.title = title;
        this.body = body;
        this.hashtags = hashtags;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.isDraft = isDraft;
        this.isActive = isActive;
        this.userId=userId;

    }

    public Set<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<String> hashtags) {
        this.hashtags = hashtags;
    }

    public Post(Long postId, Long userId, String title, String body, Set<String> hashtags, Long upvotes, Long downvotes, Boolean isDraft, Boolean isActive) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.hashtags = hashtags;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.isDraft = isDraft;
        this.isActive = isActive;
    }

    public Post(String title, String body){
        this.title=title;
        this.body=body;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Long upvotes) {
        this.upvotes = upvotes;
    }

    public Long getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Long downvotes) {
        this.downvotes = downvotes;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}