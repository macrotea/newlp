package com.lesso.newlp.home.service;

import com.lesso.newlp.home.entity.PanelEntity;
import com.lesso.newlp.home.repository.PanelRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Override
    public PanelEntity find(String panel_id) {
        return panelRepository.findOne(panel_id);
    }

    @Override
    public List<PanelEntity> getPanels(User user) {

        return null;
    }

    @Override
    public String getName(){
        return "test";
    }
}
