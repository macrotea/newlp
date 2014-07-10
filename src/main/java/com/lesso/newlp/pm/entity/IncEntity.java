package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lesso.newlp.invoice.entity.InvoiceTypeEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sean on 6/23/2014.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@Entity
@Table(name = "PM_INC",  schema = "DBO",catalog = "NEWLP")
public class IncEntity implements Serializable {
    @Id
    @Column(insertable = false,updatable = false)
    Long incId;
    @Column(insertable = false,updatable = false)
    String incName;
    @Column(insertable = false,updatable = false)
    String incShortName;

//    @JsonManagedReference("inc-invoiceType")
//    @JsonBackReference("inc-invoiceType")
    @JsonIgnore
    @ManyToMany(mappedBy = "incs")
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<InvoiceTypeEntity> invoiceTypes = new HashSet<>();

//    @JsonManagedReference("inc-client")
//    @JsonBackReference("inc-client")
@JsonIgnore
    @ManyToMany(mappedBy = "incs")
//    @LazyCollection(LazyCollectionOption.FALSE)
    Set<ClientEntity> clients = new HashSet<>();

//    @JsonManagedReference("inc-member")
//    @JsonBackReference("inc-member")
@JsonIgnore
    @ManyToMany()
    @JoinTable(name = "PM_INC_MEMBER_REL",
            joinColumns = {@JoinColumn(name = "inc_incId")},
            inverseJoinColumns = {@JoinColumn(name = "member_memberId")})
    Set<MemberEntity> members = new HashSet<MemberEntity>();

    public Long getIncId() {
        return incId;
    }

    public void setIncId(Long incId) {
        this.incId = incId;
    }

    public String getIncName() {
        return incName;
    }

    public void setIncName(String incName) {
        this.incName = incName;
    }

    public String getIncShortName() {
        return incShortName;
    }

    public void setIncShortName(String incShortName) {
        this.incShortName = incShortName;
    }

    public Set<InvoiceTypeEntity> getInvoiceTypes() {
        return invoiceTypes;
    }

    public void setInvoiceTypes(Set<InvoiceTypeEntity> invoiceTypes) {
        this.invoiceTypes = invoiceTypes;
    }

    public Set<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(Set<ClientEntity> clients) {
        this.clients = clients;
    }

    public Set<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberEntity> members) {
        this.members = members;
    }
}
