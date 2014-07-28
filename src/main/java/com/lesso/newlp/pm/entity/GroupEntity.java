package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lesso.newlp.core.entity.AbstractTimestampEntity;
import com.lesso.newlp.home.entity.PanelEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * UserDTO: Sean
 * Date: 11/14/13
 * Time: 10:44 PM
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@Entity
@Table(name = "PM_GROUP", schema = "DBO",catalog = "NEWLP")
public class GroupEntity extends AbstractTimestampEntity implements Serializable {

    @Id
    @Column(nullable = false)
    String groupId;
    @Column
    String name;

//    @JsonManagedReference("group-member")
//    @JsonBackReference("group-member")
    @ManyToMany()
    @JoinTable(name = "PM_GROUP_MEMBER_REL",
            joinColumns = {@JoinColumn(name = "group_groupId")},
            inverseJoinColumns = {@JoinColumn(name = "member_memberId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<MemberEntity> members = new HashSet<MemberEntity>();


    @ManyToMany()
    @JoinTable(name = "PM_GROUP_PANEL_REL",
            joinColumns = {@JoinColumn(name = "group_groupId")},
            inverseJoinColumns = {@JoinColumn(name = "panel_panelId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<PanelEntity> panels = new HashSet<PanelEntity>();

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberEntity> members) {
        this.members = members;
    }
}
