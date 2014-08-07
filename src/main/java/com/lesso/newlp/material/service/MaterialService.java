package com.lesso.newlp.material.service;

import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.material.model.SearchTerm;
import com.lesso.newlp.material.vo.MaterialVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Sean on 7/29/2014.
 */
public interface MaterialService {

    Page<MaterialEntity> search(SearchTerm searchTerm, Pageable pageable, String username);

    void delete(String materialNum);

    Page<MaterialVO> findByMaterialNumAndIncId(SearchTerm searchTerm, Pageable pageable, Long incId, Boolean isRelatedInc);

    MaterialVO update(Long incMaterialId, MaterialVO materialVO);


    MaterialVO addIncMaterial(String materialNum, Long incId);


    List<String> findUsedMaterialNums(List<String> materialNums);
}
