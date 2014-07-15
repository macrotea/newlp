package com.lesso.newlp.log.repository;

import com.lesso.newlp.log.entity.OperationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Sean on 7/10/2014.
 */
public interface LogRepository extends JpaRepository<OperationLogEntity,Long> {

    @Query("from OperationLogEntity  o where o.relId = :invoiceId and o.entity = 'invoice' and o.description not like '%'||cast(:auditStatus as string)||' to '||cast(:auditStatus as string)||'%' order by o.operationDate desc ")
    List<OperationLogEntity> findByInvoiceIdAndAuditStatus(@Param("invoiceId") Long invoiceId, @Param("auditStatus")Integer auditStatus);
}
