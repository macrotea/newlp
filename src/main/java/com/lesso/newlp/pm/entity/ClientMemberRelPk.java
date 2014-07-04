package com.lesso.newlp.pm.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClientMemberRelPk implements Serializable {
    @Column(nullable = false, insertable = false, updatable = false)
    String client_clientId;

    @Column(nullable = false, insertable = false, updatable = false)
    String member_memberId;
}
