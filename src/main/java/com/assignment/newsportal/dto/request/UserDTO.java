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

public class UserDTO implements Serializable {

    //        @JsonProperty(value = "user_id")
//        private Long userId;
    private Long userId;
    @JsonProperty(value = "name")
    @NotBlank(message = "Name is mandatory.")
    //@NotEmpty(message = "Name cannot be empty")
    private String name;

    //@JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "email")
    @NotBlank(message="Email is mandatory.")
    private String email;

    @JsonProperty(value = "password")
    @NotBlank(message="Password is mandatory.")
    private String pwd;

//    @JsonProperty(value = "roles")
//    private Set<String> roles;
      @JsonProperty(value = "role")
      @NotBlank(message = "Role is mandatory.")
        private String role;
    public UserDTO() {
    }

    public UserDTO(Long userId, String name, String email, String pwd, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
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

//    public Set<String> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<String> roles) {
//        this.roles = roles;
//    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    //        @JsonProperty(value = "unique_id")
//        @NotNull
//        @NotEmpty
//        private String uniqueId;
//
//        @JsonProperty(value = "")
//        @NotEmpty
//        @NotNull
//        private String employeeType;
//
//        @JsonProperty(value = "department_id")
//        private Long departmentId;
//
//        @JsonProperty(value = "employee_id")
//        private String employeeId;
//
//        @NotNull(message = "Salary cannot be null")
//        @NotEmpty(message = "Salary cannot be empty")
//        @JsonProperty(value = "role_name")
//        private String roleName;
}



