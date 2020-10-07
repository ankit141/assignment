package com.assignment.newsportal.dto.response;


import java.io.Serializable;

public class Response implements Serializable {

        private long status;
        private String message;

    public Response(long status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(){
        }


    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


