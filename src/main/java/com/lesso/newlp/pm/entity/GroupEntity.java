package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lesso.newlp.core.entity.AbstractTimestampEntity;
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
@Entity
@Table(name = "PM_GROUP", schema = "DBO",catalog = "NEWLP")
public class GroupEntity extends AbstractTimestampEntity implements Serializable {

    @Id
    @Column(nullable = false)
    String groupId;
    @Column
    String name;

    @JsonManagedReference
    @ManyToMany()
    @JoinTable(name = "PM_GROUP_MEMBER_REL",
            joinColumns = {@JoinColumn(name = "group_groupId")},
            inverseJoinColumns = {@JoinColumn(name = "member_memberId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<MemberEntity> members = new HashSet<MemberEntity>();


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
