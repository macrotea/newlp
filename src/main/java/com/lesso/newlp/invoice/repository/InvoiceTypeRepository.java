package com.lesso.newlp.invoice.repository;

import com.lesso.newlp.invoice.entity.InvoiceTypeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Sean on 6/18/2014.
 */
@RepositoryRestResource(collectionResourceRel = "invoiceType", path = "invoiceType")
public interface InvoiceTypeRepository extends PagingAndSortingRepository<InvoiceTypeEntity,Long> {
}
