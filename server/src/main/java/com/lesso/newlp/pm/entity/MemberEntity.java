package com.lesso.newlp.pm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lesso.newlp.core.entity.AuditableEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * UserDTO: Sean
 * Date: 11/14/13
 * Time: 5:16 PM
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@Entity
@Table(name = "PM_MEMBER", schema = "DBO",catalog = "NEWLP")
public class MemberEntity extends AuditableEntity implements Serializable {

    @Id
    @Column(nullable = false)
    String memberId;

    @Column(nullable = false)
    String name;
    @Column
    String password;
    @Column(nullable = false)
    Boolean enabled;

//    @JsonManagedReference("group-member")
//    @JsonBackReference("group-member")
    @ManyToMany(mappedBy="members")
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<GroupEntity> groups = new HashSet<GroupEntity>();

//    @JsonManagedReference("inc-member")
//    @JsonBackReference("inc-member")
    @ManyToMany(mappedBy="members")
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<IncEntity> incs = new HashSet<IncEntity>();

//    @JsonManagedReference("member-client")
//    @JsonBackReference("member-client")
    @ManyToMany(mappedBy="members")
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<ClientEntity> clients = new HashSet<ClientEntity>();

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }

    public Set<IncEntity> getIncs() {
        return incs;
    }

    public void setIncs(Set<IncEntity> incs) {
        this.incs = incs;
    }
}
