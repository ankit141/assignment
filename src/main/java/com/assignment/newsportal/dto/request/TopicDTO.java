package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Component
public class TopicDTO implements Serializable {
    private Long topicId;

    @JsonProperty(value="topic")
    @NotBlank(message = "Topic is mandatory.")
    private String topic;

    public TopicDTO() {
    }

    public TopicDTO(Long topicId, String topic) {
        this.topicId = topicId;
        this.topic = topic;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
