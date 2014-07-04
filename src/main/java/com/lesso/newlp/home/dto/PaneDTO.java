package com.lesso.newlp.home.dto;


import java.io.Serializable;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 2:53 PM
 */
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PaneDTO implements Serializable {

    String id;
    String name;
    String state;
    Integer count;
    Integer order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
