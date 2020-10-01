package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Component
public class HashtagDTO implements Serializable {

    private Long hashtagId;

    private Long topicId;

    @JsonProperty
    @NotBlank(message = "Hashtag name is compulsory.")
    private String hashtag;

    public HashtagDTO() {
    }

    public HashtagDTO(Long hashtagId, Long topicId, @NotBlank(message = "Hashtag name is compulsory.") String hashtag) {
        this.hashtagId = hashtagId;
        this.topicId = topicId;
        this.hashtag = hashtag;
    }

    public Long getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(Long hashtagId) {
        this.hashtagId = hashtagId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
