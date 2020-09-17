//package com.assignment.newsportal.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name="PostHashtagMap")
//public class PostHashtagMap extends Common {
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name="post_id", referencedColumnName="post_id"),
//            @JoinColumn(name="user_id", referencedColumnName="user_id")
//    })
//    private Posts post;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name="hashtag_id", referencedColumnName="hashtag_id"),
//            @JoinColumn(name="topic_id", referencedColumnName="topic_id")
//    })
//    private Hashtags hashtag;
//
//    public PostHashtagMap() {
//    }
//
//    public PostHashtagMap(Posts post, Hashtags hashtag) {
//        this.post = post;
//        this.hashtag = hashtag;
//    }
//    //private Boolean isActive;
//
//
//    public Posts getPost() {
//        return post;
//    }
//
//    public void setPost(Posts post) {
//        this.post = post;
//    }
//
//    public Hashtags getHashtag() {
//        return hashtag;
//    }
//
//    public void setHashtag(Hashtags hashtag) {
//        this.hashtag = hashtag;
//    }
//}
