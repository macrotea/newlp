package com.lesso.newlp.api.v1.invoice.controller;

import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.invoice.model.SearchTerm;
import com.lesso.newlp.invoice.repository.InvoiceRepository;
import com.lesso.newlp.invoice.repository.InvoiceTypeRepository;
import com.lesso.newlp.invoice.service.InvoiceService;
import com.lesso.newlp.log.repository.LogRepository;
import com.lesso.newlp.material.repository.CompanyMaterialRepository;
import com.lesso.newlp.material.repository.MaterialRepository;
import com.lesso.newlp.pm.repository.IncRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sean on 6/19/2014.
 */
@Controller
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    //    private static Logger logger = LogManager.getLogger();
    Logger logger = LoggerFactory.getLogger(InvoiceController.class);


    @Resource
    MaterialRepository materialRepository;

    @Resource
    InvoiceRepository invoiceRepository;

    @Resource
    InvoiceService invoiceService;

    @Resource
    InvoiceTypeRepository invoiceTypeRepository;

    @Resource
    CompanyMaterialRepository companyMaterialRepository;

    @Resource
    IncRepository incRepository;

    @Resource
    LogRepository logRepository;

    @RequestMapping()
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<InvoiceEntity>> get(Model model, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<InvoiceEntity> invoiceEntities = invoiceRepository.findAll(pageable);
        return new ResponseEntity<>(assembler.toResource(invoiceEntities), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<InvoiceEntity> add(@RequestBody InvoiceEntity invoice) {
        invoice = invoiceService.save(invoice);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @RequestMapping(value = "/{invoiceId}", method = RequestMethod.GET)
    public ResponseEntity<InvoiceEntity> get(Model model, @PathVariable("invoiceId") Long invoiceId) throws Exception {
        logger.debug("getting invoice:{}", invoiceId);
        InvoiceEntity invoice = invoiceService.findById(invoiceId);
        return new ResponseEntity<InvoiceEntity>(invoice, HttpStatus.OK);
    }

    @RequestMapping(value = "/{invoiceId}", method = RequestMethod.PUT)
    public ResponseEntity<InvoiceEntity> update(@RequestBody InvoiceEntity invoice, @PathVariable("invoiceId") Long invoiceId) throws InvocationTargetException, IllegalAccessException {
        invoice = invoiceService.update(invoice);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @RequestMapping(value = "/{invoiceId}", method = RequestMethod.PATCH)
    @Transactional
    public ResponseEntity<InvoiceEntity> patch(@RequestBody InvoiceEntity invoice, @PathVariable("invoiceId") Long invoiceId) throws InvocationTargetException, IllegalAccessException {
//        invoice = invoiceService.update(invoice);
       invoice =  invoiceService.patch(invoiceId, invoice);

        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @RequestMapping(value = "/{invoiceId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("invoiceId") Long invoiceId) {
        invoiceService.delete(invoiceId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<InvoiceEntity>> search(Model model, Pageable pageable, PagedResourcesAssembler assembler, @RequestBody SearchTerm searchTerm) {
        Page<InvoiceEntity> invoiceEntities = invoiceService.search(searchTerm, pageable);
//        Page<InvoiceEntity> invoiceEntities = invoiceRepository.queryByAuditStatus(auditStatus, pageable);
        return new ResponseEntity<PagedResources<InvoiceEntity>>(assembler.toResource(invoiceEntities), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/auditStatus/{auditStatus}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<InvoiceEntity>> searchByAuditStatus(Model model, Pageable pageable, PagedResourcesAssembler assembler, @PathVariable("auditStatus") String auditStatus,@MatrixVariable Map<String, ArrayList> matrixVars) {


        Page<InvoiceEntity> invoiceEntities;

        if(matrixVars.get("auditStatuses") == null){
            invoiceEntities = invoiceService.queryByAuditStatus(Integer.valueOf(auditStatus), pageable);
        }else{
            List<InvoiceEntity> invoices = new ArrayList<InvoiceEntity>();
            final long[] total = {0};

            List<String> auditStatuses = matrixVars.get("auditStatuses");

            for(String a : auditStatuses){
                Page<InvoiceEntity> tmp = invoiceService.queryByAuditStatus(Integer.valueOf(a), pageable);
                if (tmp.getTotalElements() > 0) {
                    invoices.addAll(tmp.getContent());
                    total[0] += tmp.getTotalElements();
                }
            }

            invoiceEntities = new PageImpl(invoices,pageable, total[0]);
        }

        return new ResponseEntity<PagedResources<InvoiceEntity>>(assembler.toResource(invoiceEntities), HttpStatus.OK);
    }

//    @RequestMapping(value = "/search/auditStatus/{auditStatus}", method = RequestMethod.GET)
//    @SuppressWarnings("unchecked")
//    public ResponseEntity<PagedResources<InvoiceEntity>> searchByAuditStatuses(Model model, Pageable pageable, PagedResourcesAssembler assembler, @MatrixVariable Map<String, String> auditStatuses) {
//
//        List<InvoiceEntity> invoices = new ArrayList<InvoiceEntity>();
//        final long[] total = {0};
//
//        auditStatuses.forEach((k, v) -> {
//            Page<InvoiceEntity>  invoiceEntities =invoiceService.queryByAuditStatus(Integer.valueOf(v), pageable);
//            if(invoiceEntities.getTotalElements() > 0){
//                invoices.addAll(invoiceEntities.getContent());
//                total[0] += invoiceEntities.getTotalElements();
//            }
//        });
//
//
////        for (String auditStatus : auditStatuses.keySet()) {
////            Page<InvoiceEntity>  invoiceEntities =invoiceService.queryByAuditStatus(Integer.valueOf(auditStatus), pageable);
////                if(invoiceEntities.getTotalElements() > 0){
////                    invoices.addAll(invoiceEntities.getContent());
////                    total += invoiceEntities.getTotalElements();
////                }
////        }
//
//        Page page = new PageImpl(invoices,pageable, total[0]);
//
//        return new ResponseEntity<PagedResources<InvoiceEntity>>(assembler.toResource(page), HttpStatus.OK);
//    }

    @RequestMapping(value = "/{invoiceId}/auditStatus/getPreAuditStatusByInvoiceId", method = RequestMethod.GET)
    public ResponseEntity<Model> getPreAuditStatusByInvoiceId(Model model, @PathVariable("invoiceId") Long invoiceId) throws Exception {
        logger.debug("getting invoice:{}", invoiceId);
        Integer auditStatus = invoiceService.getPreAuditStatusByInvoiceId(invoiceId);
        model.addAttribute("auditStatus",auditStatus);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}