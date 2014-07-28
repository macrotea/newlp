package com.lesso.newlp.home.service;

import com.lesso.newlp.home.entity.PaneEntity;
import com.lesso.newlp.home.repository.PaneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User: Sean
 * Date: 11/16/13
 * Time: 5:05 PM
 */
@Service
public class PaneServiceImpl implements PaneService {
    @Resource
    PaneRepository paneRepository;

    @Override
    public PaneEntity save(PaneEntity paneEntity) {
        return paneRepository.saveAndFlush(paneEntity);
    }


}
