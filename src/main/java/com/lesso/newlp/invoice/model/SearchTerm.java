package com.lesso.newlp.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lesso.newlp.core.modal.DateRange;

import java.io.Serializable;

/**
 * Created by Sean on 6/27/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchTerm implements Serializable{

    String num;                      //单据编号
    Long incId;                     //公司ID
    Long clientId;               //客户ID
    String carNum;                   //车号
    DateRange receivedDateRange; //交货日期
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

    public DateRange getReceivedDateRange() {
        return receivedDateRange;
    }

    public void setReceivedDateRange(DateRange receivedDateRange) {
        this.receivedDateRange = receivedDateRange;
    }
}
