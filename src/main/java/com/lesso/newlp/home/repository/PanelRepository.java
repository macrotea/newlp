package com.lesso.newlp.home.repository;

import com.lesso.newlp.home.entity.PanelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 3:53 PM
 */
@RepositoryRestResource(collectionResourceRel = "panels", path = "panels")
public interface PanelRepository extends JpaRepository<PanelEntity,String> {

    @Query("select panel from PanelEntity panel left join panel.groups groups left join groups.members members where members.memberId = :memberId")
    List<PanelEntity> findByMemberId(@Param("memberId") String memberId);

}
