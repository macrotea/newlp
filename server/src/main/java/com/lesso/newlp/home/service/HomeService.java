package com.lesso.newlp.home.service;

import com.lesso.newlp.home.entity.PanelEntity;

import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 5:21 PM
 */
public interface HomeService {

    List<PanelEntity> queryPanels(String username);

}
