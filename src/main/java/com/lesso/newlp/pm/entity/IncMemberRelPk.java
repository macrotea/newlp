package com.lesso.newlp.pm.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class IncMemberRelPk implements Serializable {
    @Column(nullable = false, insertable = false, updatable = false)
    String inc_incId;

    @Column(nullable = false, insertable = false, updatable = false)
    String member_memberId;
}
