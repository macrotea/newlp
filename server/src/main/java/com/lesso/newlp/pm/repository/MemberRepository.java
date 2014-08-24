package com.lesso.newlp.pm.repository;

import com.lesso.newlp.pm.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserDTO: Sean
 * Date: 11/14/13
 * Time: 10:20 PM
 */
public interface MemberRepository extends JpaRepository<MemberEntity,String> {

}
