package com.lesso.newlp.pm.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public  class IncClientRelPk implements Serializable {
    @Column(nullable = false, insertable = false, updatable = false)
    Long inc_incId;

    @Column(nullable = false, insertable = false, updatable = false)
    Long client_clientId;
}
