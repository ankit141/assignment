package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
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
