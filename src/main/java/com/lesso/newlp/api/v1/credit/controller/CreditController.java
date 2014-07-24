package com.lesso.newlp.api.v1.credit.controller;

import com.lesso.newlp.auth.model.CurrentUser;
import com.lesso.newlp.credit.entity.CreditEntity;
import com.lesso.newlp.credit.model.SearchTerm;
import com.lesso.newlp.credit.repository.CreditRepository;
import com.lesso.newlp.credit.service.CreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

/**
 * Created by Sean on 6/19/2014.
 */
@Controller
@RequestMapping("/api/v1/credits")
public class CreditController {

    Logger logger = LoggerFactory.getLogger(CreditController.class);

    @Resource
    CreditService creditService;

    @Resource
    CreditRepository creditRepository;

    @RequestMapping(method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<CreditEntity>> get(Model model, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<CreditEntity> creditEntities = creditRepository.queryAll( pageable);
        return new ResponseEntity<>(assembler.toResource(creditEntities), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreditEntity> add(@RequestBody CreditEntity credit, BindingResult result) {

        credit.setCreditId(null);
        if(0 == credit.getType()){
            credit.setCreditAmount(credit.getAmount().multiply(new BigDecimal(credit.getPercent())));
        }else{
            credit.setAmount(null);
            credit.setPercent(null);
        }

        credit = creditService.save(credit);
        return new ResponseEntity<>(credit, HttpStatus.OK);
    }

    @RequestMapping(value = "/{creditId}", method = RequestMethod.GET)
    public ResponseEntity<CreditEntity> get(Model model, @PathVariable("creditId") Long creditId) throws Exception {
        logger.debug("getting credit:{}",creditId);
        CreditEntity credit = creditService.findById(creditId);
        return new ResponseEntity<CreditEntity>(credit, HttpStatus.OK);
    }

    @RequestMapping(value = "/{creditId}", method = RequestMethod.PUT)
    public ResponseEntity<CreditEntity> update(@RequestBody CreditEntity credit, @PathVariable("creditId") Long creditId) throws InvocationTargetException, IllegalAccessException {


        credit.setCreditId(creditId);
        if(0 == credit.getType()){
            credit.setCreditAmount(credit.getAmount().multiply(new BigDecimal(credit.getPercent())));
        }else{
            credit.setAmount(null);
            credit.setPercent(null);
        }

        credit = creditService.update(credit);
        return new ResponseEntity<>(credit, HttpStatus.OK);
    }

    @RequestMapping(value = "/{creditId}", method = RequestMethod.PATCH)
    public ResponseEntity<CreditEntity> patch(@RequestBody CreditEntity credit, @PathVariable("creditId") Long creditId) throws InvocationTargetException, IllegalAccessException {

        if(0 == credit.getType()){
            credit.setCreditAmount(credit.getAmount().multiply(new BigDecimal(credit.getPercent())));
        }else{
            credit.setAmount(null);
            credit.setPercent(null);
        }

         credit = creditService.patch(creditId,credit);

        return new ResponseEntity<>(credit, HttpStatus.OK);
    }

    @RequestMapping(value = "/{creditId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("creditId") Long creditId) {
        creditService.delete(creditId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<CreditEntity>> search(Model model,@CurrentUser User user, Pageable pageable, PagedResourcesAssembler assembler, @RequestBody SearchTerm searchTerm) {
        Page<CreditEntity> creditEntities = creditService.search(searchTerm, pageable, user.getUsername());
        return new ResponseEntity<>(assembler.toResource(creditEntities), HttpStatus.OK);
    }

}