package com.lesso.newlp.credit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lesso.newlp.core.modal.DateRange;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Sean on 6/27/2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchTerm implements Serializable{

    Long clientId;
    String clientNum;
    Long creditId;
    Integer type;
    BigDecimal amount;                             //收款额
    Double percent;                                   //百分比
    BigDecimal creditAmount;                //信用金额
    Date insertDate;
    DateRange validDateRange; //交货日期
    String description;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientNum() {
        return clientNum;
    }

    public void setClientNum(String clientNum) {
        this.clientNum = clientNum;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateRange getValidDateRange() {
        return validDateRange;
    }

    public void setValidDateRange(DateRange validDateRange) {
        this.validDateRange = validDateRange;
    }
}
