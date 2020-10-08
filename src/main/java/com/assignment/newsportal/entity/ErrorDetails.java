package com.assignment.newsportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class ErrorDetails {

    private Date date;

    private String status;

    private String error;

    private String message;

//    private String path;

//    public ErrorDetails(Date date, String status, String error, String message, String path) {
//        this.date = date;
//        this.status = status;
//        this.error = error;
//        this.message = message;
//        this.path = path;

    public ErrorDetails(Date date, String status, String error, String message) {
        this.date = date;
        this.status = status;
        this.error = error;
        this.message = message;
    }
//    }

    public ErrorDetails() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }


}
