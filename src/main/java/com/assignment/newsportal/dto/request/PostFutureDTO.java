//package com.assignment.newsportal.dto.request;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import javax.validation.constraints.NotBlank;
//import java.io.Serializable;
//
//public class PostDTO implements Serializable {
//
//    private Long postId;
//
//
//    private String userId;
//
//    @JsonProperty(value="title")
//    @NotBlank(message="Title is mandatory.")
//    private String title;
//
//    @JsonProperty(value="hashtags")
//    private String[] hashtags;
//
//    @JsonProperty(value="body")
//    private String body;
//
//
//    private Long upvotes;
//
//
//    private Long downvotes;
//
//    public PostDTO() {
//    }
//
//    public PostDTO(Long postId, String userId, @NotBlank(message = "Title is mandatory.") String title, String[] hashtags, String body, Long upvotes, Long downvotes) {
//        this.postId = postId;
//        this.userId = userId;
//        this.title = title;
//        this.hashtags = hashtags;
//        this.body = body;
//        this.upvotes = upvotes;
//        this.downvotes = downvotes;
//    }
//
//    public Long getPostId() {
//        return postId;
//    }
//
//    public void setPostId(Long postId) {
//        this.postId = postId;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String[] getHashtags() {
//        return hashtags;
//    }
//
//    public void setHashtags(String[] hashtags) {
//        this.hashtags = hashtags;
//    }
//
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public Long getUpvotes() {
//        return upvotes;
//    }
//
//    public void setUpvotes(Long upvotes) {
//        this.upvotes = upvotes;
//    }
//
//    public Long getDownvotes() {
//        return downvotes;
//    }
//
//    public void setDownvotes(Long downvotes) {
//        this.downvotes = downvotes;
//    }
//}
