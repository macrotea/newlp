package com.lesso.newlp.home.dto;



import java.io.Serializable;
import java.util.Set;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 2:51 PM
 */
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PanelDTO implements Serializable {

    String id;
    String name;
    String state;
    String iconClass;
    Integer count;
    Integer order;

    Set<PaneDTO> panes;

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

    public Set<PaneDTO> getPanes() {
        return panes;
    }

    public void setPanes(Set<PaneDTO> panes) {
        this.panes = panes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
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

