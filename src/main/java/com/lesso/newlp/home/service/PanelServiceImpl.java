package com.lesso.newlp.home.service;

import com.lesso.newlp.home.entity.PanelEntity;
import com.lesso.newlp.home.repository.PaneRepository;
import com.lesso.newlp.home.repository.PanelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 4:24 PM
 */
@Service
public class PanelServiceImpl implements PanelService {
    @Resource
    PanelRepository panelRepository;

    @Resource
    PaneRepository paneRepository;

    @Resource
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<PanelEntity> getPanels(String username) {

        return null;
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
//    @Cacheable(value = "panelCache",key="#username + 'findByMemberId'")
    public Page<PanelEntity> findByMemberId(String username, Pageable pageable) {
        return panelRepository.findAll(pageable);
    }

    @Override
    public List<PanelEntity> findByMemberId(String memberId) {

//        String sql = "SELECT *\n" +
//                "FROM ST_HOME_PANEL PANEL\n" +
//                "  LEFT JOIN PM_GROUP_PANEL_REL GP_REL\n" +
//                "    ON GP_REL.PANEL_PANELID = PANEL.PANELID\n" +
//                "  LEFT JOIN PM_GROUP_MEMBER_REL GM_REL\n" +
//                "    ON GM_REL.GROUP_GROUPID = GP_REL.GROUP_GROUPID\n" +
//                "WHERE GM_REL.MEMBER_MEMBERID = :memberId";
//
//        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
//        mapSqlParameterSource.addValue("memberId", memberId);
//
//
//        List<PanelEntity> panelEntities = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new RowMapper<PanelEntity>() {
//
//            @Override
//            public PanelEntity mapRow(ResultSet resultSet, int i) throws SQLException {
//                PanelEntity panelEntity = new PanelEntity();
//                panelEntity.setPanelId(resultSet.getString("panelId"));
//                panelEntity.setName(resultSet.getString("name"));
//                panelEntity.setCount(resultSet.getInt("count"));
//                panelEntity.setOrder(resultSet.getInt("order_"));
//                panelEntity.setState(resultSet.getString("state"));
//
//                panelEntity.setPanes(paneRepository.findByPanelId(panelEntity.getPanelId()));
//                return panelEntity;
//            }
//        });


        return panelRepository.findByMemberId(memberId);
    }
}
