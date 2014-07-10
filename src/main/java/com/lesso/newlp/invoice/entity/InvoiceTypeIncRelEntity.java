package com.lesso.newlp.invoice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lesso.newlp.core.entity.AbstractTimestampEntity;
import com.lesso.newlp.pm.entity.IncEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;

/**
 * UserDTO: Sean
 * Date: 11/14/13
 * Time: 10:56 PM
 */
@Entity
@Table(name = "INV_INVOICETYPE_INC_REL", schema = "DBO",catalog = "NEWLP")
public class InvoiceTypeIncRelEntity extends AbstractTimestampEntity implements Serializable  {

    @EmbeddedId
    private InvoiceTypeIncRelPk invoiceTypeIncRelPk;

    @JsonBackReference("invoiceType-invoiceTypeIncRel")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoiceType_invoiceTypeId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    InvoiceTypeEntity invoiceType;

    @JsonBackReference("inc-invoiceTypeIncRel")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inc_incId", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    IncEntity inc;

    public InvoiceTypeIncRelPk getInvoiceTypeIncRelPk() {
        return invoiceTypeIncRelPk;
    }

    public void setInvoiceTypeIncRelPk(InvoiceTypeIncRelPk invoiceTypeIncRelPk) {
        this.invoiceTypeIncRelPk = invoiceTypeIncRelPk;
    }

    public InvoiceTypeEntity getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceTypeEntity invoiceType) {
        this.invoiceType = invoiceType;
    }

    public IncEntity getInc() {
        return inc;
    }

    public void setInc(IncEntity inc) {
        this.inc = inc;
    }
}
