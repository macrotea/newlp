package com.lesso.newlp.home.repository;

import com.lesso.newlp.home.entity.PaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * User: Sean
 * Date: 11/16/13
 * Time: 5:06 PM
 */
public interface PaneRepository extends JpaRepository<PaneEntity,String> {

    @Query("select pane from PaneEntity  pane where pane.panel.panelId = :panelId")
    Set<PaneEntity> findByPanelId(@Param("panelId") String panelId);
}
