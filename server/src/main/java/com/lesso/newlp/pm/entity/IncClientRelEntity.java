package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lesso.newlp.core.entity.AuditableEntity;
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
@Table(name = "PM_INC_CLIENT_REL", schema = "DBO",catalog = "NEWLP")
public class IncClientRelEntity extends AuditableEntity implements Serializable  {

    @EmbeddedId
    private IncClientRelPk incClientRelPk;

    @JsonBackReference("inc-incClientRel")
    @ManyToOne()
    @JoinColumn(name = "inc_incId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    IncEntity inc;

    @JsonBackReference("client-incClientRel")
    @ManyToOne()
    @JoinColumn(name = "client_clientId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    ClientEntity client;

    public IncClientRelPk getIncClientRelPk() {
        return incClientRelPk;
    }

    public void setIncClientRelPk(IncClientRelPk incClientRelPk) {
        this.incClientRelPk = incClientRelPk;
    }

    public IncEntity getInc() {
        return inc;
    }

    public void setInc(IncEntity inc) {
        this.inc = inc;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
