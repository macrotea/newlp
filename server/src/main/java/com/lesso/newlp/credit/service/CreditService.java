package com.lesso.newlp.credit.service;

import com.lesso.newlp.credit.entity.CreditEntity;
import com.lesso.newlp.credit.model.SearchTerm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Sean on 7/3/2014.
 */
public interface CreditService {

    CreditEntity save(CreditEntity creditEntity);

    CreditEntity findById(Long creditId) throws Exception;

    CreditEntity update(CreditEntity creditEntity);

    void delete(Long creditId);

    Page<CreditEntity> search(SearchTerm searchTerm, Pageable pageable, String memberId);

    CreditEntity patch(Long creditId, CreditEntity credit);
}
