package com.lesso.newlp.invoice.service;

import com.lesso.newlp.invoice.entity.InvoiceTypeEntity;
import com.lesso.newlp.invoice.repository.InvoiceTypeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Sean on 6/23/2014.
 */
@Service
public class InvoiceTypeServiceImpl implements InvoiceTypeService {

    @Resource
    InvoiceTypeRepository invoiceTypeRepository;

    @Override
    public InvoiceTypeEntity save(InvoiceTypeEntity invoiceType) {

        return invoiceTypeRepository.save(invoiceType);
    }
}
