package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class TopicDTO implements Serializable {
    private Long topicId;

    @JsonProperty(value="topic")
    @NotBlank(message = "Please provide topic name.")
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
