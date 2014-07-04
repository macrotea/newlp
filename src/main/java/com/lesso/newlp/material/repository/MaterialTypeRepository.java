package com.lesso.newlp.material.repository;

import com.lesso.newlp.material.entity.MaterialTypeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Sean on 6/18/2014.
 */
@RepositoryRestResource(collectionResourceRel = "materialType", path = "materialType")
public interface MaterialTypeRepository extends PagingAndSortingRepository<MaterialTypeEntity,Long> {

}
