package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lesso.newlp.credit.entity.CreditEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sean on 6/23/2014.
 */
@Entity
@Table(name = "PM_CLIENT", schema = "DBO",catalog = "NEWLP")
public class ClientEntity  implements Serializable {

    @Id
    @Column(insertable = false,updatable = false)
    Long clientId;
    @Column(insertable = false,updatable = false)
    String clientNum;
    @Column(insertable = false,updatable = false)
    String clientName;

    @JsonBackReference
    @ManyToMany()
    @JoinTable(name = "PM_INC_CLIENT_REL",
            joinColumns = {@JoinColumn(name = "client_clientId")},
            inverseJoinColumns = {@JoinColumn(name = "inc_incId")})
    Set<IncEntity> incs = new HashSet<IncEntity>();

    @JsonBackReference
    @ManyToMany()
    @JoinTable(name = "PM_CLIENT_MEMBER_REL",
            joinColumns = {@JoinColumn(name = "client_clientId")},
            inverseJoinColumns = {@JoinColumn(name = "member_memberId")})
    Set<MemberEntity> members = new HashSet<MemberEntity>();

    @JsonBackReference
    @OneToMany(mappedBy = "client")
    Set<CreditEntity> credits = new HashSet<>();
//    @JsonManagedReference
//    @ManyToMany(mappedBy="clients")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    Set<IncEntity> incs = new HashSet<IncEntity>();


    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientNum() {
        return clientNum;
    }

    public void setClientNum(String clientNum) {
        this.clientNum = clientNum;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Set<IncEntity> getIncs() {
        return incs;
    }

    public void setIncs(Set<IncEntity> incs) {
        this.incs = incs;
    }

    public Set<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberEntity> members) {
        this.members = members;
    }

    public Set<CreditEntity> getCredits() {
        return credits;
    }

    public void setCredits(Set<CreditEntity> credits) {
        this.credits = credits;
    }
}
