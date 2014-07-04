package com.lesso.newlp.material.repository;

import com.lesso.newlp.material.entity.MaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Sean on 6/18/2014.
 */
@RepositoryRestResource(collectionResourceRel = "materials", path = "materials")
public interface MaterialRepository extends PagingAndSortingRepository<MaterialEntity,String> {

    @Query("select m from MaterialEntity m where (m.name like %:searchTerm% or m.materialNum like %:searchTerm% ) and m.active is true")
    Page<MaterialEntity> findByNameOrNumLike(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("select m from MaterialEntity m where m.name = :name")
    Page<MaterialEntity> findByName(@Param("name") String name, Pageable pageable);
}
