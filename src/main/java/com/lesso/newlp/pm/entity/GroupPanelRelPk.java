package com.lesso.newlp.pm.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GroupPanelRelPk implements Serializable {

    @Column(nullable = false, insertable = false, updatable = false)
    String group_groupId;

    @Column(nullable = false, insertable = false, updatable = false)
    String panel_panelId;

    public String getGroup_groupId() {
        return group_groupId;
    }

    public void setGroup_groupId(String group_groupId) {
        this.group_groupId = group_groupId;
    }

    public String getPanel_panelId() {
        return panel_panelId;
    }

    public void setPanel_panelId(String panel_panelId) {
        this.panel_panelId = panel_panelId;
    }
}
