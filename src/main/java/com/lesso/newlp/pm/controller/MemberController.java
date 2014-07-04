package com.lesso.newlp.pm.controller;

import com.lesso.newlp.pm.entity.GroupEntity;
import com.lesso.newlp.pm.entity.MemberEntity;
import com.lesso.newlp.pm.repository.GroupRepository;
import com.lesso.newlp.pm.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by Sean on 6/7/2014.
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Resource
    MemberRepository memberRepository;

    @Resource
    GroupRepository groupRepository;

    @RequestMapping
    public MemberEntity getMember(){
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupId("CUSTOMER");
        groupEntity.setName("Customer");
        Set<MemberEntity> memberEntities=groupEntity.getMembers();

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName("微城");
        memberEntity.setMemberId("gh_297b6e7601ad");
        memberEntity.setEnabled(true);
        memberEntity.setPassword("12345");
        memberEntity= memberRepository.saveAndFlush(memberEntity);

        memberEntities.add(memberEntity);
        groupRepository.saveAndFlush(groupEntity);

        return memberEntity;
    }
}
