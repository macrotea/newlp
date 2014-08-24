package com.lesso.newlp.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public  class InvoiceTypeIncRelPk implements Serializable {
    @Column(nullable = false,insertable =false,updatable = false)
    Long invoiceType_invoiceTypeId;

    @Column(nullable = false, insertable = false, updatable = false)
    Long inc_incId;
}
