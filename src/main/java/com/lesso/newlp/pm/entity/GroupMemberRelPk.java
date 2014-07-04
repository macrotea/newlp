package com.lesso.newlp.pm.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GroupMemberRelPk implements Serializable {
    @Column(nullable = false, insertable = false, updatable = false)
    String member_memberId;

    @Column(nullable = false, insertable = false, updatable = false)
    String group_groupId;
}
