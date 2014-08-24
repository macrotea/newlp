package com.lesso.newlp.invoice.vo;

import java.util.Set;

/**
 * Created by Sean on 6/17/2014.
 */
public class InvoiceTypeVO {

    Integer id;
    Integer type;                        //单据类别,1:入库,0:出库
    String name;
    String[] companyIds;
    Boolean active=true;                     //默认为1，0为删除
    Set<InvoiceVO> invoices;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(String[] companyIds) {
        this.companyIds = companyIds;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<InvoiceVO> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<InvoiceVO> invoices) {
        this.invoices = invoices;
    }
}
