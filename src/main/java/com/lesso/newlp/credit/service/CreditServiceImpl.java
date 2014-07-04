package com.lesso.newlp.credit.service;

import com.lesso.newlp.credit.entity.CreditEntity;
import com.lesso.newlp.credit.model.SearchTerm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Sean on 7/3/2014.
 */
@Service
public class CreditServiceImpl implements CreditService {
    @Override
    public CreditEntity save(CreditEntity creditEntity) {
        return null;
    }

    @Override
    public CreditEntity findById(Long creditId) throws Exception {
        return null;
    }

    @Override
    public CreditEntity update(CreditEntity creditEntity) {
        return null;
    }

    @Override
    public void delete(Long creditId) {

    }

    @Override
    public Page<CreditEntity> search(SearchTerm searchTerm, Pageable pageable) {
        return null;
    }
}
