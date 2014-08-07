package com.lesso.newlp.material.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lesso.newlp.core.entity.AuditableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by Sean on 6/17/2014.
 */
@Entity
@Table(name = "MAT_MATERIAL", schema = "DBO",catalog = "NEWLP")
public class MaterialEntity extends AuditableEntity implements Serializable {

    @Id
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

    @Column(columnDefinition ="bit NULL DEFAULT ((1))")
    Boolean active=true;                     //默认为1，0为删除

//    @JsonBackReference("materialType-material")
    @ManyToOne()
    MaterialTypeEntity materialType;

    @JsonBackReference("materialAttribute-material")
    @ManyToOne
    MaterialAttributeEntity materialAttribute;

    @JsonIgnore
    @OneToMany(mappedBy = "material")
    Set<CompanyMaterialEntity> companyMaterials;

//    @RestResource(exported = false)
//    @OneToMany(mappedBy = "material",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    Set<InvoiceDetailEntity> invoiceDetails;


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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MaterialTypeEntity getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialTypeEntity materialType) {
        this.materialType = materialType;
    }

    public MaterialAttributeEntity getMaterialAttribute() {
        return materialAttribute;
    }

    public void setMaterialAttribute(MaterialAttributeEntity materialAttribute) {
        this.materialAttribute = materialAttribute;
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

    public Set<CompanyMaterialEntity> getCompanyMaterials() {
        return companyMaterials;
    }

    public void setCompanyMaterials(Set<CompanyMaterialEntity> companyMaterials) {
        this.companyMaterials = companyMaterials;
    }
}
