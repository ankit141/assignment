package com.assignment.newsportal.dto.request;



//import com.assignment.newsportal.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class UserDTO implements Serializable {


    private Long userId;
    @JsonProperty("name")
    @NotBlank(message = "Please provide name.")
    private String name;

    @JsonProperty("email")
    @NotBlank(message="Please provide email.")
    private String email;


    @JsonProperty(value = "role")
    @NotBlank
    private String role;
    public UserDTO() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}



