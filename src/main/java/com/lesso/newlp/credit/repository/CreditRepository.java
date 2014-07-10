package com.lesso.newlp.credit.repository;

import com.lesso.newlp.credit.entity.CreditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Sean on 6/18/2014.
 */
@RepositoryRestResource(collectionResourceRel = "credits", path = "credits")
public interface CreditRepository extends PagingAndSortingRepository<CreditEntity,Long> {

    @Query("select cre from CreditEntity cre where cre.active = true")
    Page<CreditEntity> queryAll(Pageable pageable);
}
