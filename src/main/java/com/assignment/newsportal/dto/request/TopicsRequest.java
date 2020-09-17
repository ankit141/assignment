package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TopicsRequest {

    @JsonProperty
    private List<String> follow;

    public TopicsRequest() {
    }

    public TopicsRequest(List<String> follow) {
        this.follow = follow;


    }

    public List<String> getFollow() {
        return follow;
    }

    public void setFollow(List<String> follow) {
        this.follow = follow;
    }
}
