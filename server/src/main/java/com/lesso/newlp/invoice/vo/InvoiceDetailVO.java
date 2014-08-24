package com.lesso.newlp.invoice.vo;

import com.lesso.newlp.material.entity.MaterialEntity;

import java.math.BigDecimal;

/**
 * Created by Sean on 6/17/2014.
 */
public class InvoiceDetailVO {

    Integer id;

    Double orderCount;           //订单数量
    Double deliveryCount;      //实发数量
    BigDecimal amount;                 //总金额
    String remark;                //备注
    Boolean active=true;                     //默认为1，0为删除


    InvoiceVO invoice;

    MaterialEntity material;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Double orderCount) {
        this.orderCount = orderCount;
    }

    public Double getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Double deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public InvoiceVO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceVO invoice) {
        this.invoice = invoice;
    }

    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
    }
}
