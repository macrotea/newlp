package com.lesso.newlp.invoice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lesso.newlp.core.entity.AbstractTimestampEntity;
import com.lesso.newlp.pm.entity.IncEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sean on 6/17/2014.
 */
@Entity
@Table(name = "INV_INVOICE_TYPE", schema = "DBO",catalog = "NEWLP")
public class InvoiceTypeEntity extends AbstractTimestampEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long invoiceTypeId;
    Integer type;                        //单据类别,1:入库,0:出库
    String name;
    @Column(columnDefinition ="bit NULL DEFAULT ((1))")
    Boolean active=true;                     //默认为1，0为删除

//    @JsonIgnore
//    @RestResource
//    @OneToMany(mappedBy = "invoiceType",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    Set<InvoiceEntity> invoices;

    @JsonBackReference
    @ManyToMany()
    @JoinTable(name = "INV_INVOICETYPE_INC_REL",
            joinColumns = {@JoinColumn(name = "invoiceType_invoiceTypeId")},
            inverseJoinColumns = {@JoinColumn(name = "inc_incId")})
    Set<IncEntity> incs = new HashSet<>();

    public Long getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(Long invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<IncEntity> getIncs() {
        return incs;
    }

    public void setIncs(Set<IncEntity> incs) {
        this.incs = incs;
    }
}
