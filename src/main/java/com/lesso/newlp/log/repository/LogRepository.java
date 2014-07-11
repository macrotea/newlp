package com.lesso.newlp.log.repository;

import com.lesso.newlp.log.entity.OperationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Sean on 7/10/2014.
 */
public interface LogRepository extends JpaRepository<OperationLogEntity,Long> {

}
