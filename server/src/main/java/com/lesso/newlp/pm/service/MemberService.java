package com.lesso.newlp.pm.service;

import com.lesso.newlp.pm.entity.MemberEntity;

import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/15/13
 * Time: 2:32 PM
 */
public interface MemberService {

    MemberEntity find(String username);

    List<String> findGroupsByUsername(String username);
}
