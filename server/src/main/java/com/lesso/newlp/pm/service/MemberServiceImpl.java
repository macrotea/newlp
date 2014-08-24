package com.lesso.newlp.pm.service;

import com.lesso.newlp.pm.entity.MemberEntity;
import com.lesso.newlp.pm.repository.MemberRepository;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/15/13
 * Time: 2:33 PM
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberRepository memberRepository;

    @Resource
    JdbcDaoSupport jdbcDaoSupport;

    @Override
    public MemberEntity find(String username) {
        return memberRepository.findOne(username);
    }

    @Override
    public List<String> findGroupsByUsername(String username){
       return  jdbcDaoSupport.getJdbcTemplate().queryForList("select m.GROUP_ID_ from ACT_ID_MEMBERSHIP m where m.USER_ID_ = ? ",new String[]{username},String.class);
    }
}
