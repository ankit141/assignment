package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TopicsRequest {

    @JsonProperty
    private String[] follow;

    public TopicsRequest() {
    }

    public TopicsRequest(String[] follow) {
        this.follow = follow;
    }

    public String[] getFollow() {
        return follow;
    }

    public void setFollow(String[] follow) {
        this.follow = follow;
    }

    //    public TopicsRequest(List<String> follow) {
//        this.follow = follow;
//
//
//    }

//    public List<String> getFollow() {
//        return follow;
//    }
//
//    public void setFollow(List<String> follow) {
//        this.follow = follow;
//    }
}
