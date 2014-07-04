package com.lesso.newlp.material.service;

import com.lesso.newlp.material.entity.MaterialTypeEntity;
import com.lesso.newlp.material.repository.MaterialTypeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Sean on 6/23/2014.
 */
@Service
public class MaterialTypeServiceImpl implements MaterialTypeService {

    @Resource
    MaterialTypeRepository materialTypeRepository;

    @Override
    public MaterialTypeEntity save(MaterialTypeEntity materialTypeEntity) {
        return materialTypeRepository.save(materialTypeEntity);
    }
}
