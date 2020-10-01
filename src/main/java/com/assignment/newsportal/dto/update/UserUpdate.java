package com.assignment.newsportal.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UserUpdate implements Serializable{
    private Long userId;
    @JsonProperty
    @NotBlank(message="Name is mandatory.")
    private String name;

    @JsonProperty
    @NotBlank(message = "Email is mandatory.")
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
