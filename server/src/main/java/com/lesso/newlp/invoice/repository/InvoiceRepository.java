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
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    @Query("select inv from InvoiceEntity inv where inv.auditStatus = :auditStatus  and inv.active = true")
    Page<InvoiceEntity> queryByAuditStatus(@Param("auditStatus") Integer auditStatus, Pageable pageable);

    @Query("select inv from InvoiceEntity  inv where  inv.invoiceNum like :invoiceNum order by inv.invoiceNum desc")
    Page<InvoiceEntity> getByMaxLikeInvoiceNum(@Param("invoiceNum") String invoiceNum, Pageable pageable);

    @Query("select distinct inv from InvoiceEntity  inv left join inv.client.members climem left join inv.inc.members incmem left join inv.logs  log " +
            "where inv.auditStatus = ?1 and  climem.memberId = ?2 and incmem.memberId = ?2 and inv.active = true ")
    Page<InvoiceEntity> findByAuditStatus(Integer auditStatus, String memberId, Pageable pageable);

    @Query("select distinct inv from InvoiceEntity  inv left join inv.client.members climem left join inv.inc.members incmem left join inv.logs  log " +
            "where inv.auditStatus >= ?1  and  climem.memberId = ?2 and incmem.memberId = ?2 and inv.active = true  " +
            "and (log.description like '%\"modifiedVal\":\"'||cast(?1 as string)||'\",\"name\":\"auditStatus\",\"originalVal\":\"0\"%' or " +
            "log.description like '%\"modifiedVal\":\"'||cast(?1 as string)||'\",\"name\":\"auditStatus\",\"originalVal\":\"'||cast(?1 - 10 as string)||'\"%') ")
    Page<InvoiceEntity> findByAuditedStatus(Integer auditedStatus, String  memberId, Pageable pageable);

}
