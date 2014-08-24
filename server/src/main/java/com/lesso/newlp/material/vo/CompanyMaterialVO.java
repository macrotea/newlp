package com.lesso.newlp.material.vo;

import java.math.BigDecimal;

/**
 * Created by Sean on 6/17/2014.
 */
public class CompanyMaterialVO{
    Integer id;
    String companyId;              //公司ID
    BigDecimal balance;                        //期初余额
    Double inventory;                   //期初库存
    Boolean active=true;                     //默认为1，0为删除

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
}
