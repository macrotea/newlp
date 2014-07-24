package com.lesso.newlp.home.service;

import com.lesso.newlp.home.entity.PaneEntity;
import com.lesso.newlp.home.entity.PanelEntity;
import com.lesso.newlp.invoice.repository.InvoiceRepository;
import com.lesso.newlp.invoice.service.InvoiceService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 5:22 PM
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Resource
    PanelService panelService;
    @Resource
    PaneService paneService;

    @Resource
    InvoiceRepository invoiceRepository;

    @Resource
    InvoiceService invoiceService;

    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<PanelEntity> queryPanels(String username){

//        List<PanelDTO> panelDTOs = mapperFacade.mapAsList(panelService.queryPanels(username), PanelDTO.class);
        List<PanelEntity> panelEntities = mapperFacade.mapAsList(panelService.findByMemberId(username), PanelEntity.class);

        int count = 0;
        for(PanelEntity panel : panelEntities){
            for(PaneEntity pane : panel.getPanes()){
                Set<Integer> auditStatuses = new HashSet<Integer>();

                switch (pane.getPaneId()){
                    case "CUSTOMER_SERVICE_ORDER_RECEIVES":
                        auditStatuses.add(20);
                        count = invoiceService.queryCountByAuditStatus(auditStatuses,username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                    case "CUSTOMER_SERVICE_ORDER_DRAFTS":
                        auditStatuses.add(30);
                        count = invoiceService.queryCountByAuditStatus(auditStatuses,username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                    case "CUSTOMER_SERVICE_ORDER_SUBMITTED":
                        auditStatuses.add(40);
                        count = invoiceService.queryCountByOriginalAuditStatus(40, username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                    case "DEPOT_RECEIVES":
                        auditStatuses.add(40);
                        count = invoiceService.queryCountByAuditStatus(auditStatuses,username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                    case "DEPOT_AUDIT_UNAUDITED":
                        auditStatuses.add(50);
                        auditStatuses.add(60);
                        count = invoiceService.queryCountByAuditStatus(auditStatuses,username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                    case "DEPOT_AUDIT_AUDITED":
                        auditStatuses.add(70);
                        count = invoiceService.queryCountByOriginalAuditStatus(70, username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                    case "CUSTOMER_SERVICE_CONFIRM_RECEIVES":
                        auditStatuses.add(70);
                        count = invoiceService.queryCountByAuditStatus(auditStatuses,username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                    case "CUSTOMER_SERVICE_CONFIRM_UNAUDITED":
                        auditStatuses.add(80);
                        count = invoiceService.queryCountByAuditStatus(auditStatuses,username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                    case "CUSTOMER_SERVICE_CONFIRM_AUDITED":
                        auditStatuses.add(90);
                        count = invoiceService.queryCountByOriginalAuditStatus(90, username);
                        pane.setCount(count);
                        panel.setCount(panel.getCount() + count);
                        break;
                }

            }
        }
        return panelEntities;
    }
}
