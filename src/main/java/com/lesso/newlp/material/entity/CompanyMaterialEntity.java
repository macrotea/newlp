package com.lesso.newlp.material.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lesso.newlp.core.entity.AbstractTimestampEntity;
import com.lesso.newlp.pm.entity.IncEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Sean on 6/17/2014.
 */
@Entity
@Table(name = "MAT_COMPANY_MATERIAL", schema = "DBO",catalog = "NEWLP")
public class CompanyMaterialEntity extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long companyMaterialId;
    BigDecimal balance;                        //期初余额
    Double inventory;                   //期初库存
    @Column(columnDefinition ="bit NULL DEFAULT ((1))")
    Boolean active=true;                     //默认为1，0为删除

    @JsonBackReference("inc-companyMaterial")
    @ManyToOne
    IncEntity inc;                          //公司

    public Long getCompanyMaterialId() {
        return companyMaterialId;
    }

    public void setCompanyMaterialId(Long companyMaterialId) {
        this.companyMaterialId = companyMaterialId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Double getInventory() {
        return inventory;
    }

    public void setInventory(Double inventory) {
        this.inventory = inventory;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public IncEntity getInc() {
        return inc;
    }

    public void setInc(IncEntity inc) {
        this.inc = inc;
    }
}
