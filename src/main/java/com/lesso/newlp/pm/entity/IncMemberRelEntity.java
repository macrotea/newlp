package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "PM_INC_MEMBER_REL", schema = "DBO",catalog = "NEWLP")
public class IncMemberRelEntity extends AbstractTimestampEntity implements Serializable  {

    @EmbeddedId
    private IncMemberRelPk incMemberRelPk;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "inc_incId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    IncEntity inc;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "member_memberId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    MemberEntity member;

    public IncMemberRelPk getIncMemberRelPk() {
        return incMemberRelPk;
    }

    public void setIncMemberRelPk(IncMemberRelPk incMemberRelPk) {
        this.incMemberRelPk = incMemberRelPk;
    }

    public IncEntity getInc() {
        return inc;
    }

    public void setInc(IncEntity inc) {
        this.inc = inc;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
}