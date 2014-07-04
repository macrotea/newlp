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
@Table(name = "PM_CLIENT_MEMBER_REL", schema = "DBO",catalog = "NEWLP")
public class ClientMemberRelEntity extends AbstractTimestampEntity implements Serializable  {

    @EmbeddedId
    private ClientMemberRelPk clientMemberRelPk;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "client_clientId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    ClientEntity client;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "member_memberId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    MemberEntity member;

    public ClientMemberRelPk getClientMemberRelPk() {
        return clientMemberRelPk;
    }

    public void setClientMemberRelPk(ClientMemberRelPk clientMemberRelPk) {
        this.clientMemberRelPk = clientMemberRelPk;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
}