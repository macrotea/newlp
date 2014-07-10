package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lesso.newlp.core.entity.AbstractTimestampEntity;
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
@Table(name = "PM_GROUP_MEMBER_REL", schema = "DBO",catalog = "NEWLP")
public class GroupMemberRelEntity extends AbstractTimestampEntity implements Serializable  {

    @EmbeddedId
    private GroupMemberRelPk groupMemberRelPk;

//    @JsonBackReference("member-groupMemberRel")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_memberId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    MemberEntity member;

//    @JsonBackReference("group-groupMemberRel")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_groupId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    GroupEntity group;

    public GroupMemberRelPk getGroupMemberRelPk() {
        return groupMemberRelPk;
    }

    public void setGroupMemberRelPk(GroupMemberRelPk groupMemberRelPk) {
        this.groupMemberRelPk = groupMemberRelPk;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }
}
