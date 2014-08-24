package com.lesso.newlp.auth.service;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sean on 8/12/2014.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<GrantedAuthority> getGrantedAuthority(String username) {
        String sql = "SELECT DISTINCT\n" +
                "  authority\n" +
                "FROM\n" +
                "  (\n" +
                "    SELECT\n" +
                "      g.groupId,\n" +
                "      g.name,\n" +
                "      ga.authority,\n" +
                "      gm.member_memberId\n" +
                "    FROM\n" +
                "      PM_GROUP_AUTHORITIES ga\n" +
                "      LEFT JOIN PM_GROUP_MEMBER_REL gm ON gm.group_groupId = ga.group_groupId\n" +
                "      LEFT JOIN PM_GROUP g ON ga.group_groupId = g.groupId\n" +
                "    UNION\n" +
                "    SELECT\n" +
                "      g.groupId,\n" +
                "      g.name,\n" +
                "      g.groupId AS authority,\n" +
                "      gm.member_memberId\n" +
                "    FROM\n" +
                "      PM_GROUP g\n" +
                "      LEFT JOIN PM_GROUP_MEMBER_REL gm ON gm.group_groupId = g.groupId\n" +
                "  ) a\n" +
                "WHERE\n" +
                "  a.member_memberId = :username";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("username",username);

        List<GrantedAuthority> authorities = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, (rs, i) -> new SimpleGrantedAuthority(rs.getString("authority")));

        return authorities;
    }
}
