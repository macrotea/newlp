package com.lesso.newlp.invoice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sean on 6/27/2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchTerm implements Serializable{

    String num;                      //单据编号
    Long incId;                     //公司ID
    Long clientId;               //客户ID
    String carNum;                   //车号
    Date startDateOfReceived;   //交货日期
    Date endDateOfReceived;
    Integer invoiceTypeId;
    Integer auditStatus;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Long getIncId() {
        return incId;
    }

    public void setIncId(Long incId) {
        this.incId = incId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
//
    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public Date getStartDateOfReceived() {
        return startDateOfReceived;
    }

    public void setStartDateOfReceived(Date startDateOfReceived) {
        this.startDateOfReceived = startDateOfReceived;
    }

    public Date getEndDateOfReceived() {
        return endDateOfReceived;
    }

    public void setEndDateOfReceived(Date endDateOfReceived) {
        this.endDateOfReceived = endDateOfReceived;
    }

    public Integer getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(Integer invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}
