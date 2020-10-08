package com.assignment.newsportal.dto.request;

import com.assignment.newsportal.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordChange {

    @JsonProperty(value="New Password")
    @NotBlank(message = "Please enter new password.")
    @Size(min = 8, max = 15,message = "Password should have between 8 and 15 characters.")
    @Pattern(regexp = Constants.PASSWORD,message = "Please enter valid password.")
    private String newPwd;

    @JsonProperty(value="Confirm Password")
    @NotBlank(message = "Please confirm new password.")
    private String confirm;

    public PasswordChange() {
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
