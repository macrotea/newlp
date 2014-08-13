package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * Created by Sean on 8/12/2014.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@Entity
@Table(name = "PM_GROUP_AUTHORITIES", schema = "DBO",catalog = "NEWLP")
public class GroupAuthorityEntity {

    @EmbeddedId
    private GroupAuthorityPK groupAuthorityPK;


    @ManyToOne()
    @JoinColumn(name = "group_groupId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    GroupEntity group;

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }
}
