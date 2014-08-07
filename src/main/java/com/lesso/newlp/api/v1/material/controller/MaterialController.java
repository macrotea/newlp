package com.lesso.newlp.api.v1.material.controller;

import com.alibaba.fastjson.JSON;
import com.lesso.newlp.auth.model.CurrentUser;
import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.material.entity.MaterialTypeEntity;
import com.lesso.newlp.material.model.SearchTerm;
import com.lesso.newlp.material.repository.MaterialRepository;
import com.lesso.newlp.material.repository.MaterialTypeRepository;
import com.lesso.newlp.material.service.MaterialService;
import com.lesso.newlp.material.vo.MaterialVO;
import com.lesso.newlp.pm.entity.IncEntity;
import com.lesso.newlp.pm.entity.MemberEntity;
import com.lesso.newlp.pm.repository.IncRepository;
import com.lesso.newlp.pm.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Sean on 6/18/2014.
 */
@Controller
@RequestMapping("/api/v1/materials")
@SuppressWarnings("unchecked")
public class MaterialController {

    @Resource
    MaterialRepository materialRepository;

    @Resource
    MaterialTypeRepository materialTypeRepository;

    @Resource
    MaterialService materialService;

    @Resource
    IncRepository incRepository;

    @Resource
    MemberRepository memberRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MaterialEntity> add(@RequestBody MaterialEntity materialEntity) {
        materialEntity = materialRepository.save(materialEntity);
        return new ResponseEntity<>(materialEntity, HttpStatus.OK);
    }

    @RequestMapping(value = "/{materialNum:.+}", method = RequestMethod.POST)
    public ResponseEntity<MaterialEntity> update(@RequestBody MaterialEntity material){
        material = materialRepository.save(material);
        return new ResponseEntity<>(material, HttpStatus.OK);
    }

    @RequestMapping(value = "/{materialNum:.+}", method = RequestMethod.PUT)
    public ResponseEntity<MaterialEntity> update(@RequestBody MaterialEntity material, @PathVariable("materialNum") String materialNum) throws InvocationTargetException, IllegalAccessException {
        List<MaterialEntity> materialEntityList = materialRepository.findByMaterialNum(materialNum);

        MaterialEntity materialEntity = null;
        if(0 < materialEntityList.size()){
             materialEntity = materialEntityList.get(0);
        }

        MaterialTypeEntity materialTypeEntity = materialTypeRepository.findOne(material.getMaterialType().getMaterialTypeId());

        materialEntity.setMaterialType(materialTypeEntity);
        materialEntity.setUnit(material.getUnit());
        materialEntity.setLength(material.getLength());
        materialEntity.setAuxiliaryUnitOne(material.getAuxiliaryUnitOne());
        materialEntity.setAuxiliaryUnitTwo(material.getAuxiliaryUnitTwo());
        materialEntity.setConversionRateOne(material.getConversionRateOne());
        materialEntity.setPrice(material.getPrice());
        materialEntity.setProductTypeOne(material.getProductTypeOne());
        materialEntity.setProductTypeTwo(material.getProductTypeTwo());

        materialRepository.save(materialEntity);
        return new ResponseEntity<>(materialEntity, HttpStatus.OK);
    }

    @RequestMapping(value = "/addIncMaterial/{materialNum:.+}", method = RequestMethod.POST)
    public ResponseEntity<MaterialVO> addIncMaterial(@RequestBody MaterialVO materialVO, @PathVariable("materialNum") String materialNum,@CurrentUser User user) throws InvocationTargetException, IllegalAccessException {
        MemberEntity memberEntity = memberRepository.findOne(user.getUsername());

        for(IncEntity inc : memberEntity.getIncs()){
            materialVO = materialService.addIncMaterial(materialNum,inc.getIncId());
        }
        return new ResponseEntity<MaterialVO>(materialVO, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateIncMaterial/{incMaterialId}", method = RequestMethod.PUT)
    public ResponseEntity<MaterialVO> updateIncMaterial(@RequestBody MaterialVO materialVO, @PathVariable("incMaterialId") Long incMaterialId) throws InvocationTargetException, IllegalAccessException {
        materialVO = materialService.update(incMaterialId, materialVO);
        return new ResponseEntity<MaterialVO>(materialVO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{materialNum:.+}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("materialNum") String materialNum) {
        materialService.delete(materialNum);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<MaterialEntity>> search(Model model,@CurrentUser User user, Pageable pageable, PagedResourcesAssembler assembler, @RequestBody SearchTerm searchTerm) {
        Page<MaterialEntity> materialEntities = materialService.search(searchTerm, pageable, user.getUsername());
        return new ResponseEntity<PagedResources<MaterialEntity>>(assembler.toResource(materialEntities), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/findByMaterialNumAndIncId", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<MaterialEntity>> findByMaterialNumAndIncId(Model model,@CurrentUser User user, Pageable pageable, PagedResourcesAssembler assembler, @RequestBody SearchTerm searchTerm) {

        MemberEntity memberEntity = memberRepository.findOne(user.getUsername());

        Page<MaterialVO> materialEntities = null;

            for(IncEntity inc : memberEntity.getIncs()){
                if(searchTerm.getIsRelated()){
                    materialEntities = materialService.findByMaterialNumAndIncId(searchTerm, pageable, inc.getIncId(),true);
                }else{
                    materialEntities = materialService.findByMaterialNumAndIncId(searchTerm, pageable, inc.getIncId(),false);
                }
            }



        return new ResponseEntity<PagedResources<MaterialEntity>>(assembler.toResource(materialEntities), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/findUsedMaterialNums", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findDeleteAbleMaterialNums(String materialNums){
        List<String> list = (List<String>) JSON.parse(materialNums);
        List<String> usedMaterialNums = materialService.findUsedMaterialNums(list);
        return usedMaterialNums;
    }

//    @RequestMapping("/search")
//    public HttpEntity<Model> search(Model model,@RequestParam(defaultValue = "10") int size){
//        return new ResponseEntity<>(model, HttpStatus.OK);
//    }

}
