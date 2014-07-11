package com.lesso.newlp.invoice.service;

import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.invoice.model.SearchTerm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Sean on 6/20/2014.
 */
public interface InvoiceService {

    InvoiceEntity save(InvoiceEntity invoice);

    InvoiceEntity findById(Long invoiceId) throws Exception;

    InvoiceEntity update(InvoiceEntity invoice) throws InvocationTargetException, IllegalAccessException;

    InvoiceEntity patch(Long invoiceId, InvoiceEntity invoice) throws InvocationTargetException, IllegalAccessException;

    void delete(Long invoiceId);

    Page<InvoiceEntity> queryByAuditStatus(Integer auditStatus, Pageable pageable);

    Page<InvoiceEntity> search(SearchTerm searchTerm, Pageable pageable);
}
