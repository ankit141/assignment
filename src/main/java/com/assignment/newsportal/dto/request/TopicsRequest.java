package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class TopicsRequest {

    @JsonProperty
    private Set<String> follow;

    public TopicsRequest() {
    }

    public TopicsRequest(Set<String> follow) {
        this.follow = follow;
    }

    public Set<String> getFollow() {
        return follow;
    }

    public void setFollow(Set<String> follow) {
        this.follow = follow;
    }
}
