package com.lesso.newlp.home.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lesso.newlp.pm.entity.GroupEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 2:51 PM
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@UUID",scope = PanelEntity.class)
@Entity
@Table(name = "ST_HOME_PANEL", schema = "DBO", catalog = "NEWLP")
public class PanelEntity implements Serializable {

    @Id
    @Column
    String panelId;
    String name;
    String state;
    String iconClass;
    @Column(name = "count",updatable = false)
    Integer count;
    @Column(name = "order_")
    Integer order;

    @OneToMany(mappedBy = "panel")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("order ASC")
    Set<PaneEntity> panes = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy="panels")
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<GroupEntity> groups = new HashSet<GroupEntity>();

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PaneEntity> getPanes() {
        return panes;
    }

    public void setPanes(Set<PaneEntity> panes) {
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

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }
}
