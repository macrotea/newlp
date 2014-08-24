package com.lesso.newlp.invoice.repository;

import com.lesso.newlp.invoice.entity.InvoiceEntity;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

/**
 * Created by Sean on 6/23/2014.
 */
@RepositoryEventHandler
public class InvoiceEventHandler {

    @HandleBeforeSave(InvoiceEntity.class)
    public void handleInvoiceSave(InvoiceEntity invoice) {
        invoice.getInvoiceDetails().forEach(d -> d.setInvoice(invoice));
    }

}
