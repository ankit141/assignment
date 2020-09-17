package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordChange {

    @JsonProperty(value="New Password")
    private String newPwd;

    @JsonProperty(value="Confirm Password")
    private String confirm;

    public PasswordChange() {
    }

    public PasswordChange(String newPwd, String confirm) {
        this.newPwd = newPwd;
        this.confirm = confirm;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
