package com.lesso.newlp.home.service;

import com.lesso.newlp.home.dto.PaneDTO;
import com.lesso.newlp.home.dto.PanelDTO;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    MapperFacade mapperFacade;

    @Override
    public List<PanelDTO> getPanels(User user){
        List<PanelDTO> panelDTOs = mapperFacade.mapAsList(panelService.getPanels(user), PanelDTO.class);
        int count;
        for(PanelDTO panelDTO : panelDTOs){
            for(PaneDTO paneDTO : panelDTO.getPanes()){
                if("inbox".equals(paneDTO.getId())){
//                    count = (int) taskService.createTaskQuery().taskAssignee(username).orderByTaskId().asc().count();
//                    paneDTO.setCount(count);
//                    panelDTO.setCount(panelDTO.getCount() + count);
                }
                if("queued".equals(paneDTO.getId())){
//                    count = queuedListQuery.getQueuedListCountByUsername(username);
//                    paneDTO.setCount(count);
//                    panelDTO.setCount(panelDTO.getCount()+count);
                }
                if("instance".equals(paneDTO.getId())){
//                    count = historyService.createHistoricProcessInstanceQuery().startedBy(username).unfinished().list().size();
//                    paneDTO.setCount(count);
//                    panelDTO.setCount(panelDTO.getCount()+count);
                }
            }
        }
        return panelDTOs;
    }
}
