package com.assignment.newsportal.dto.request;




import com.assignment.newsportal.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTotalDTO implements Serializable {

    private Long userId;
    @JsonProperty("name")
    @NotBlank(message = "Please provide name.")
    private String name;

    @JsonProperty("email")
    @Pattern(regexp = Constants.EMAIL,message = "Please enter valid email.")
    @NotBlank(message="Please provide email.")
    private String email;

    @JsonProperty("password")
    @Size(min = 8, max = 15,message = "Password should have between 8 and 15 characters.")
    @Pattern(regexp = Constants.PASSWORD,message = "Please enter valid password.")
    @NotBlank(message="Please provide password.")
    private String pwd;



    public UserTotalDTO() {
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




