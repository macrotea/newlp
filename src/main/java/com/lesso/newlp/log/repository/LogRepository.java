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

    @Query("from OperationLogEntity  o where o.relId = :invoiceId and o.entity = 'invoice'")
    List<OperationLogEntity> findByInvoiceId(@Param("invoiceId") Long invoiceId);


}
