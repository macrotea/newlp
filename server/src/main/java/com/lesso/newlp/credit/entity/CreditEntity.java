package com.lesso.newlp.credit.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lesso.newlp.core.entity.AuditableEntity;
import com.lesso.newlp.pm.entity.ClientEntity;
import com.lesso.newlp.pm.entity.IncEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Sean on 7/3/2014.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@UUID",scope = CreditEntity.class)

@Entity
@Table(name = "CRE_CREDIT", schema = "DBO", catalog = "NEWLP")
public class CreditEntity extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long creditId;
    @Column(columnDefinition ="bit NULL DEFAULT ((1))")
    Boolean active=true;                     //默认为1，0为删除
    Integer type;
    BigDecimal amount;                             //收款额
    @Column(name="percent_")
    Double percent;                                   //百分比
    BigDecimal creditAmount;                //信用金额
    Date insertDate;
    Date validDate;
    Date expiryDate;
    String description;

    @ManyToOne()
    ClientEntity client;

    @ManyToOne()
    IncEntity inc;

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public IncEntity getInc() {
        return inc;
    }

    public void setInc(IncEntity inc) {
        this.inc = inc;
    }
}
