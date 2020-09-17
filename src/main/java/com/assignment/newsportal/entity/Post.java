package com.assignment.newsportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {
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

    @JsonIgnore
    @Column(name="created_at")
    //@JsonFormat(pattern = Constants.TIMESTAMP)
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public Post() {
    }

    public Post(String title, String body){
        this.title=title;
        this.body=body;
    }
//
//    public Post(Long postId, String title, Long downvotes, Boolean isDraft, Boolean isActive) {
//        this.postId = postId;
//        this.title = title;
//        this.downvotes = downvotes;
//        this.isDraft = isDraft;
//        this.isActive = isActive;
//    }

    public Post(Long postId, Long userId, String title, String body, Boolean isDraft, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.upvotes = Long.valueOf(0);
        this.downvotes = Long.valueOf(0);
        this.isDraft = isDraft;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

        public Long getPostId() {
            return postId;
        }

        public void setPostId (Long postId){
            this.postId = postId;
        }

        public Long getUserId () {
            return userId;
        }

        public void setUserId (Long userId){
            this.userId = userId;
        }

        public String getTitle () {
            return title;
        }

        public void setTitle (String title){
            this.title = title;
        }

        public String getBody () {
            return body;
        }

        public void setBody (String body){
            this.body = body;
        }

        public Long getUpvotes () {
            return upvotes;
        }

        public void setUpvotes (Long upvotes){
            this.upvotes = upvotes;
        }

        public Long getDownvotes () {
            return downvotes;
        }

        public void setDownvotes (Long downvotes){
            this.downvotes = downvotes;
        }

        public Boolean getDraft () {
            return isDraft;
        }

        public void setDraft (Boolean draft){
            isDraft = draft;
        }

        public Boolean getActive () {
            return isActive;
        }

        public void setActive (Boolean active){
            isActive = active;
        }

        public LocalDateTime getCreatedAt () {
            return createdAt;
        }

        public void setCreatedAt (LocalDateTime createdAt){
            this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt () {
            return updatedAt;
        }

        public void setUpdatedAt (LocalDateTime updatedAt){
            this.updatedAt = updatedAt;
        }

    }