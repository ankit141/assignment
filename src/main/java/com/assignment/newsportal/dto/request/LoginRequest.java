package com.assignment.newsportal.dto.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message="Name is mandatory.")
    private String name;

    @NotBlank(message="Password is mandatory.")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


