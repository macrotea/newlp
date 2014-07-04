package com.lesso.newlp.api.v1.invoice.controller;

import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.invoice.model.SearchTerm;
import com.lesso.newlp.invoice.repository.InvoiceRepository;
import com.lesso.newlp.invoice.repository.InvoiceTypeRepository;
import com.lesso.newlp.invoice.service.InvoiceService;
import com.lesso.newlp.material.repository.CompanyMaterialRepository;
import com.lesso.newlp.material.repository.MaterialRepository;
import com.lesso.newlp.pm.repository.IncRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

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

    @RequestMapping()
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<InvoiceEntity>> get(Model model, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<InvoiceEntity> invoiceEntities = invoiceRepository.findAll(pageable);
        return new ResponseEntity<>(assembler.toResource(invoiceEntities), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<InvoiceEntity> add(@RequestBody InvoiceEntity invoice, BindingResult result) {
        invoice = invoiceService.save(invoice);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @RequestMapping(value = "/{invoiceId}", method = RequestMethod.GET)
    public ResponseEntity<InvoiceEntity> get(Model model, @PathVariable("invoiceId") Long invoiceId) throws Exception {
        logger.debug("getting invoice:{}",invoiceId);
        InvoiceEntity invoice = invoiceService.findById(invoiceId);
        return new ResponseEntity<InvoiceEntity>(invoice, HttpStatus.OK);
    }

    @RequestMapping(value = "/{invoiceId}", method = RequestMethod.PUT)
    public ResponseEntity<InvoiceEntity> update(@RequestBody InvoiceEntity invoice, @PathVariable("invoiceId") Long invoiceId) throws InvocationTargetException, IllegalAccessException {
        invoice = invoiceService.update(invoice);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @RequestMapping(value = "/{invoiceId}", method = RequestMethod.PATCH)
    public ResponseEntity<InvoiceEntity> patch(@RequestBody InvoiceEntity invoice, @PathVariable("invoiceId") Long invoiceId) throws InvocationTargetException, IllegalAccessException {
//        invoice = invoiceService.update(invoice);

        InvoiceEntity invoiceEntity = invoiceRepository.findOne(invoiceId);

        if(!Objects.isNull(invoice.getAuditStatus())){
            invoiceEntity.setAuditStatus(invoice.getAuditStatus());
        }

        invoice= invoiceRepository.save(invoiceEntity);

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
        return new ResponseEntity<>(assembler.toResource(invoiceEntities), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/auditStatus/{auditStatus}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagedResources<InvoiceEntity>> searchByAudit(Model model, Pageable pageable, PagedResourcesAssembler assembler, @PathVariable("auditStatus") Integer auditStatus) {
        Page<InvoiceEntity> invoiceEntities = invoiceService.queryByAuditStatus(auditStatus,pageable);
//        Page<InvoiceEntity> invoiceEntities = invoiceRepository.queryByAuditStatus(auditStatus, pageable);
        return new ResponseEntity<>(assembler.toResource(invoiceEntities), HttpStatus.OK);
    }
}