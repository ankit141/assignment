package com.assignment.newsportal.dto.request;



//import com.assignment.newsportal.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;


//@JsonIgnoreProperties(ignoreUnknown = true)
//    @Getter
//    @Setter

public class UserTotalDTO implements Serializable {

    //        @JsonProperty(value = "user_id")
//        private Long userId;
    private Long userId;
    @JsonProperty(value = "name")
    @NotBlank(message = "Name is mandatory.")
    private String name;

    @JsonProperty(value = "email")
    @NotBlank(message="Email is mandatory.")
    private String email;

    @JsonProperty(value = "password")
    @NotBlank(message="Password is mandatory.")
    private String pwd;

    //    @JsonProperty(value = "roles")
//    private Set<String> roles;

    public UserTotalDTO() {
    }

    public UserTotalDTO(Long userId, @NotBlank(message = "Name is mandatory.") String name, @NotBlank(message = "Email is mandatory.") String email, @NotBlank(message = "Password is mandatory.") String pwd) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}




