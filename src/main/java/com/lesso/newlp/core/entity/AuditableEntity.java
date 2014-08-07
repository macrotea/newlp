package com.lesso.newlp.core.entity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Sean on 6/6/2014.
 */
@MappedSuperclass
public abstract class AuditableEntity {

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created",updatable = false,columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
//    private Date created;
//
//    @Temporal(TemporalType.TIMESTAMP)
////    @Column(name = "updated",updatable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
//    @Column(name = "updated",updatable = false)
//    private Date updated;

    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created",updatable = false,columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date created;
    private String lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updated",updatable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Column(name = "updated",updatable = false)
    private Date lastModified;


    @PrePersist
    protected void onCreate() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        lastModified = created = new Date();
        lastModifiedBy = createdBy = user.getUsername();
    }

    @PreUpdate
    protected void onUpdate() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        lastModified = new Date();
        lastModifiedBy = user.getUsername();

    }
}
