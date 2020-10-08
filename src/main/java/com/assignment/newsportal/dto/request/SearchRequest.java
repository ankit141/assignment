package com.assignment.newsportal.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest {

    @JsonProperty("search")
    @NotBlank(message = "Please provide search value.")
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
