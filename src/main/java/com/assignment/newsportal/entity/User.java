package com.assignment.newsportal.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")

public class User extends Common {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name= "name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String pwd;

    @Column(name="roles")
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name="is_active")
    private boolean isActive;

    public User() {
    }
    public User(String username, String email, String password) {
        this.name = username;
        this.email = email;
        this.pwd = password;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public User(Long userId, String name, String email, String pwd, ERole role, boolean isActive) {
    this.userId = userId;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.role=role;
        this.isActive = isActive;
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



    public Set<ERole> getRoles() {
        Set<ERole> s=new HashSet<>();
        s.add(role);
        return s;
    }



    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


}
