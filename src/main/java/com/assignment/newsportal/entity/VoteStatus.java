package com.assignment.newsportal.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="vote_status")
public class VoteStatus {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="post_id")
    private Long postId;

    @Column(name="upvoted")
    private Boolean upvoted;

    @Column(name="downvoted")
    private Boolean downvoted;

    @Column(name="is_active")
    private Boolean isActive;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public VoteStatus(Long userId, Long postId, boolean isActive, boolean upvoted, boolean downvoted) {
        this.userId=userId;
        this.postId=postId;
        this.isActive=isActive;
        this.upvoted=upvoted;
        this.downvoted=downvoted;
    }

    @PrePersist
    protected void onCreate() {
        // this.createdBy=;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        //this.updatedBy=;
        this.updatedAt = LocalDateTime.now();
    }

    public VoteStatus(Long userId, Long postId, Boolean isActive){
        this.userId=userId;
        this.postId=postId;
        this.isActive=isActive;

    }

    public VoteStatus() {
    }

    public VoteStatus(Long id, Long userId, Long postId, Boolean upvoted, Boolean downvoted, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.upvoted = upvoted;
        this.downvoted = downvoted;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Boolean getUpvoted() {
        return upvoted;
    }

    public void setUpvoted(Boolean upvoted) {
        this.upvoted = upvoted;
    }

    public Boolean getDownvoted() {
        return downvoted;
    }

    public void setDownvoted(Boolean downvoted) {
        this.downvoted = downvoted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
