package com.lesso.newlp.material.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Sean on 6/27/2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchTerm implements Serializable{

    String queryString;
    Long type;

    Long incId;


    Boolean active;
    Boolean isRelated;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getIncId() {
        return incId;
    }

    public void setIncId(Long incId) {
        this.incId = incId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getIsRelated() {
        return isRelated;
    }

    public void setIsRelated(Boolean isRelated) {
        this.isRelated = isRelated;
    }
}
