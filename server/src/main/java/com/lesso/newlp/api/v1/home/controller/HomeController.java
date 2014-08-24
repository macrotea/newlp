package com.lesso.newlp.api.v1.home.controller;

import com.lesso.newlp.auth.model.CurrentUser;
import com.lesso.newlp.home.entity.PanelEntity;
import com.lesso.newlp.home.service.HomeService;
import com.lesso.newlp.home.service.PanelService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 2:49 PM
 */
@Controller
@RequestMapping("/api/v1/home")
@SuppressWarnings("unchecked")
public class HomeController {

    @Resource
    HomeService homeService;
    @Resource
    PanelService panelService;


//    @RequestMapping
//    public ResponseEntity<Model> render(Model model, @AuthenticationPrincipal User user) {
//        java.util.List<PanelEntity> panelEntity = panelRepository.findAll();
//        return new ResponseEntity<>(service.getModel(model,user), HttpStatus.OK);
//    }

    @RequestMapping("/panels")
    public HttpEntity<List<PanelEntity>> get(Model model, Pageable pageable, PagedResourcesAssembler assembler,@CurrentUser User user) {
        List<PanelEntity> panels = homeService.queryPanels(user.getUsername());
        return new ResponseEntity<>(panels, HttpStatus.OK);
    }
}
