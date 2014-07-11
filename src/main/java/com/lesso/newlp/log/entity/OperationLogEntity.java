package com.lesso.newlp.log.entity;

import com.lesso.newlp.core.entity.AbstractTimestampEntity;

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
    String relId;
    Long incId;
    String memberId;
    String description;

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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIncId() {
        return incId;
    }

    public void setIncId(Long incId) {
        this.incId = incId;
    }
}
