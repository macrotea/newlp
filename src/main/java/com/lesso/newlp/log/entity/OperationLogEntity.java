package com.lesso.newlp.log.entity;

import com.lesso.newlp.core.entity.AbstractTimestampEntity;
import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.pm.entity.IncEntity;
import com.lesso.newlp.pm.entity.MemberEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sean on 7/10/2014.
 */
@Entity
@Table(name = "LOG_OPERATION", schema = "DBO",catalog = "NEWLP")
public class OperationLogEntity extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long operationLogId;
    Date operationDate;
    String entity;
    String type;
    @Column(columnDefinition = "TEXT")
    String description;

    @ManyToOne
    IncEntity inc;

    @ManyToOne
    InvoiceEntity invoice;

    @ManyToOne
    MemberEntity member;


    public Long getOperationLogId() {
        return operationLogId;
    }

    public void setOperationLogId(Long operationLogId) {
        this.operationLogId = operationLogId;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IncEntity getInc() {
        return inc;
    }

    public void setInc(IncEntity inc) {
        this.inc = inc;
    }

}
