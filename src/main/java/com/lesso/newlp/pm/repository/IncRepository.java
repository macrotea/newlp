package com.lesso.newlp.pm.repository;

import com.lesso.newlp.pm.entity.IncEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * UserDTO: Sean
 * Date: 11/14/13
 * Time: 10:20 PM
 */
@RepositoryRestResource(collectionResourceRel = "incs", path = "incs")
public interface IncRepository extends JpaRepository<IncEntity,Long> {

    @Query("select inc from IncEntity  inc left join inc.members  incMem  where incMem.memberId = :memberId")
    Page<IncEntity> findByMemberId(@Param("memberId") String memberId,Pageable pageable);
}
