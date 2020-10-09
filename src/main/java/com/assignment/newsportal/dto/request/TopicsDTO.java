package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
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
