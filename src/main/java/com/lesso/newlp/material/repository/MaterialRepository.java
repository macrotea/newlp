package com.lesso.newlp.material.repository;

import com.lesso.newlp.material.entity.CompanyMaterialEntity;
import com.lesso.newlp.material.entity.MaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by Sean on 6/18/2014.
 */
@RepositoryRestResource(collectionResourceRel = "materials", path = "materials")
public interface MaterialRepository extends JpaRepository<MaterialEntity, String> {

    @Query("select m from MaterialEntity m left join m.companyMaterials cm left join cm.inc.members mem where (m.name like '%'||:searchTerm||'%' or m.materialNum like '%'||:searchTerm||'%' ) and m.active = true and mem.memberId= :memberId")
    Page<MaterialEntity> findByNameOrNumLike(@Param("searchTerm") String searchTerm, @Param("memberId") String memberId,Pageable pageable);

    @Query("select m from MaterialEntity m where m.name = :name and m.active = true")
    Page<MaterialEntity> findByName(@Param("name") String name, Pageable pageable);

    @Query("select m from MaterialEntity m where TRIM(m.materialNum)  = :materialNum and m.active = true")
    List<MaterialEntity> findByMaterialNum(@Param("materialNum") String materialNum);

    @Query("select m from MaterialEntity m where TRIM(m.name)  = :materialName and m.active = true")
    List<MaterialEntity> findByMaterialName(@Param("materialName") String materialName);

    @RestResource(exported = false)
    @Query("select m from MaterialEntity m where  m.active = true")
    Page<MaterialEntity> search(Pageable pageable);

    @RestResource(exported = false)
    @Query("select m from MaterialEntity m where (TRIM(m.materialNum)  like '%'|| :queryString||'%' or  TRIM(m.name) like '%'|| :queryString||'%') and m.materialType.materialTypeId = :materialTypeId and m.active = true")
    Page<MaterialEntity> search(@Param("queryString") String queryString, @Param("materialTypeId") Long materialTypeId, Pageable pageable);

    @RestResource(exported = false)
    @Query("select m from MaterialEntity m where (TRIM(m.materialNum)  like '%'|| :queryString||'%' or  TRIM(m.name) like '%'|| :queryString||'%') and m.active = true")
    Page<MaterialEntity> search(@Param("queryString") String queryString, Pageable pageable);

    @RestResource(exported = false)
    @Query("select m from MaterialEntity m where m.materialType.materialTypeId = :materialTypeId")
    Page<MaterialEntity> search(@Param("materialTypeId") Long materialTypeId, Pageable pageable);

    @Query("select cm from CompanyMaterialEntity  cm left join cm.inc.members mem where cm.active = true and mem.memberId= :memberId and cm.material.materialNum = :materialNum")
    CompanyMaterialEntity getCompanyMaterialByMaterialNum(@Param("materialNum") String materialNum,@Param("memberId") String memberId);

}
