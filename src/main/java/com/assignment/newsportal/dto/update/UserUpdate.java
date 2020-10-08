package com.assignment.newsportal.dto.update;

import com.assignment.newsportal.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdate implements Serializable{
    private Long userId;
    @JsonProperty
    @NotBlank(message="Please provide name.")
    private String name;

    @JsonProperty
    @NotBlank(message = "Please provide email.")
    @Pattern(regexp = Constants.EMAIL,message = "Please enter valid email.")
    private String email;

    public UserUpdate() {
    }

    public UserUpdate(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
