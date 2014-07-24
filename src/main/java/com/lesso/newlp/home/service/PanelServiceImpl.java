package com.lesso.newlp.home.service;

import com.lesso.newlp.home.entity.PanelEntity;
import com.lesso.newlp.home.repository.PanelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<PanelEntity> getPanels(String username) {

        return null;
    }

    @Override
    public String getName(){
        return "test";
    }

    @Override
//    @Cacheable(value = "panelCache",key="#username + 'findByMemberId'")
    public Page<PanelEntity> findByMemberId(String username, Pageable pageable) {
        return panelRepository.findAll(pageable);
    }

    @Override
    public List<PanelEntity> findByMemberId(String username) {
        return panelRepository.findAll();
    }
}
