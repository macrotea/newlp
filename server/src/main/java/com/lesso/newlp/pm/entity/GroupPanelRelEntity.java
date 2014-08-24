package com.lesso.newlp.pm.entity;

import com.lesso.newlp.core.entity.AuditableEntity;
import com.lesso.newlp.home.entity.PanelEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;

/**
 * UserDTO: Sean
 * Date: 11/14/13
 * Time: 10:56 PM
 */
@Entity
@Table(name = "PM_GROUP_PANEL_REL", schema = "DBO", catalog = "NEWLP")
public class GroupPanelRelEntity extends AuditableEntity implements Serializable {

    @EmbeddedId
    private GroupPanelRelPk groupPanelRelPk;


    @ManyToOne
    @JoinColumn(name = "group_groupId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    GroupEntity group;


    @ManyToOne
    @JoinColumn(name = "panel_panelId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    PanelEntity panel;

    public GroupPanelRelPk getGroupPanelRelPk() {
        return groupPanelRelPk;
    }

    public void setGroupPanelRelPk(GroupPanelRelPk groupPanelRelPk) {
        this.groupPanelRelPk = groupPanelRelPk;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public PanelEntity getPanel() {
        return panel;
    }

    public void setPanel(PanelEntity panel) {
        this.panel = panel;
    }
}
