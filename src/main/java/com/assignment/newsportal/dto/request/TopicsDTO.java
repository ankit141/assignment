package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class TopicsDTO {

    @JsonProperty
    private Set<String> topics;

    public TopicsDTO() {
    }

    public TopicsDTO(Set<String> topics) {
        this.topics = topics;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }
}
