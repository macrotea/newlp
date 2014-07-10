package com.lesso.newlp.home.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 2:53 PM
 */
@Entity
@Table(name = "ST_HOME_PANE", schema = "DBO", catalog = "NEWLP")
public class PaneEntity implements Serializable {

    @Id
    @Column
    String paneId;
    String name;
    String state;
    Integer count;
    @Column(name = "order_")
    Integer order;

    @JsonBackReference("panel-pane")
    @ManyToOne()
    PanelEntity panel;

    public String getPaneId() {
        return paneId;
    }

    public void setPaneId(String paneId) {
        this.paneId = paneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PanelEntity getPanel() {
        return panel;
    }

    public void setPanel(PanelEntity panel) {
        this.panel = panel;
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
