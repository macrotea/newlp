package com.lesso.newlp.api.v1.material.controller;

import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.material.repository.MaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Sean on 6/18/2014.
 */
@Controller
@RequestMapping("/api/v1/material")
@SuppressWarnings("unchecked")
public class MaterialController {

    @Resource
    MaterialRepository materialRepository;

    @RequestMapping("/search")
    public HttpEntity<PagedResources<MaterialEntity>> search(Model model,Pageable pageable,PagedResourcesAssembler assembler,String queryStr){
        Page<MaterialEntity> materials = materialRepository.findAll(pageable);
        return new ResponseEntity<>(assembler.toResource(materials), HttpStatus.OK);
    }
//    @RequestMapping("/search")
//    public HttpEntity<Model> search(Model model,@RequestParam(defaultValue = "10") int size){
//        return new ResponseEntity<>(model, HttpStatus.OK);
//    }

}
