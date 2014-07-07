package com.lesso.newlp.home.service;

import com.lesso.newlp.home.entity.PanelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 4:22 PM
 */

public interface PanelService {


    List<PanelEntity> getPanels(User user);

    String getName();

    Page<PanelEntity> findByMemberId(String username, Pageable pageable);
}
