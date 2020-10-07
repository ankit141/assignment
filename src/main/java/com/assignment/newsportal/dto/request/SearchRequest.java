package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRequest {

    private String search;

    public SearchRequest() {
    }

    public SearchRequest(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
