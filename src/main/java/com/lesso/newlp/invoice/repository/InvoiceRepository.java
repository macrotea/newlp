package com.lesso.newlp.invoice.repository;

import com.lesso.newlp.invoice.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Sean on 6/18/2014.
 */
@RepositoryRestResource(collectionResourceRel = "invoices", path = "invoices")
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long> {

    @Query("select inv from InvoiceEntity inv where inv.auditStatus = :auditStatus  and inv.active = true")
    Page<InvoiceEntity> queryByAuditStatus(@Param("auditStatus") Integer auditStatus, Pageable pageable);


}
