package com.lesso.newlp.home.repository;

import com.lesso.newlp.home.entity.PanelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 3:53 PM
 */
@RepositoryRestResource(collectionResourceRel = "panels", path = "panels")
public interface PanelRepository extends PagingAndSortingRepository<PanelEntity,String> {
}
