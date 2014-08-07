package com.lesso.newlp.material.vo;

import java.math.BigDecimal;

/**
 * Created by Sean on 6/18/2014.
 */
public class MaterialVO {
    Long incMaterialId;
    String materialNum;                                   //物料编码
    String name;                                 //物料名称
    String unit;                                   //主计量单位
    String auxiliaryUnitOne; //辅计量单位1
    String auxiliaryUnitTwo; //辅计量单位2
    Double conversionRateOne;//换算率1
    Double conversionRateTwo;//换算率2
    BigDecimal price;                             //单价
    Double length;
    String productTypeOne;
    String productTypeTwo;
    Double inventory;
    Boolean active;                     //默认为1，0为删除
    Boolean isRelated;
    BigDecimal incPrice;                             //单价

    public Long getIncMaterialId() {
        return incMaterialId;
    }

    public void setIncMaterialId(Long incMaterialId) {
        this.incMaterialId = incMaterialId;
    }

    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getProductTypeOne() {
        return productTypeOne;
    }

    public void setProductTypeOne(String productTypeOne) {
        this.productTypeOne = productTypeOne;
    }

    public String getProductTypeTwo() {
        return productTypeTwo;
    }

    public void setProductTypeTwo(String productTypeTwo) {
        this.productTypeTwo = productTypeTwo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getIsRelated() {
        return isRelated;
    }

    public void setIsRelated(Boolean isRelated) {
        this.isRelated = isRelated;
    }

    public BigDecimal getIncPrice() {
        return incPrice;
    }

    public void setIncPrice(BigDecimal incPrice) {
        this.incPrice = incPrice;
    }

    public Double getInventory() {
        return inventory;
    }

    public void setInventory(Double inventory) {
        this.inventory = inventory;
    }
}
