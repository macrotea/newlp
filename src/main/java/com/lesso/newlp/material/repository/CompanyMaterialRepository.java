package com.lesso.newlp.material.repository;

import com.lesso.newlp.material.entity.CompanyMaterialEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Sean on 6/21/2014.
 */
@RepositoryRestResource(collectionResourceRel = "companyMaterials", path = "companyMaterials")
public interface CompanyMaterialRepository extends PagingAndSortingRepository<CompanyMaterialEntity,Long> {
}