package com.lesso.newlp.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lesso.newlp.core.entity.AuditableEntity;
import com.lesso.newlp.material.entity.MaterialEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Sean on 6/17/2014.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@UUID",scope = InvoiceDetailEntity.class)
@Entity
@Table(name = "INV_INVOICE_DETAIL", schema = "DBO",catalog = "NEWLP")
public class InvoiceDetailEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long invoiceDetailId;
    Double orderCount;           //订单数量
    Double deliveryCount;      //实发数量
    BigDecimal amount;                 //总金额
    String remark;                //备注
    @Column(columnDefinition ="bit NULL DEFAULT ((1))")
//    @Column(columnDefinition ="BIT(1)  DEFAULT b'1'")
    Boolean active=true;                     //默认为1，0为删除

    /*==========================================
    在单据端保存物料的数据，是为了避免物料端数据更改时，造成历史数据的改变*/
    String unit;                                   //主计量单位
    String auxiliaryUnitOne; //辅计量单位1
    String auxiliaryUnitTwo; //辅计量单位1
    Double conversionRateOne;//换算率1
    Double conversionRateTwo;//换算率2
    BigDecimal price;                             //单价
    BigDecimal incPrice;                             //单价
    /*==========================================*/

    @JsonIgnore
    @ManyToOne
    InvoiceEntity invoice;

    @ManyToOne
    MaterialEntity material;

    public Long getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(Long invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
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

    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
    }
//
    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAuxiliaryUnitOne() {
        return auxiliaryUnitOne;
    }

    public void setAuxiliaryUnitOne(String auxiliaryUnitOne) {
        this.auxiliaryUnitOne = auxiliaryUnitOne;
    }

    public String getAuxiliaryUnitTwo() {
        return auxiliaryUnitTwo;
    }

    public void setAuxiliaryUnitTwo(String auxiliaryUnitTwo) {
        this.auxiliaryUnitTwo = auxiliaryUnitTwo;
    }

    public Double getConversionRateOne() {
        return conversionRateOne;
    }

    public void setConversionRateOne(Double conversionRateOne) {
        this.conversionRateOne = conversionRateOne;
    }

    public Double getConversionRateTwo() {
        return conversionRateTwo;
    }

    public void setConversionRateTwo(Double conversionRateTwo) {
        this.conversionRateTwo = conversionRateTwo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getIncPrice() {
        return incPrice;
    }

    public void setIncPrice(BigDecimal incPrice) {
        this.incPrice = incPrice;
    }
}
