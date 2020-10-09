package com.assignment.newsportal.entity;



import java.util.Date;


public class ErrorDetails {

    private Date date;

    private String status;

    private String error;

    private String message;



    public ErrorDetails(Date date, String status, String error, String message) {
        this.date = date;
        this.status = status;
        this.error = error;
        this.message = message;
    }

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




}
