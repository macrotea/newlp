package com.lesso.newlp.home.service;

import com.lesso.newlp.home.entity.PanelEntity;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 4:22 PM
 */

public interface PanelService {

    PanelEntity find(String panel_id);

    List<PanelEntity> getPanels(User user);

    String getName();
}
