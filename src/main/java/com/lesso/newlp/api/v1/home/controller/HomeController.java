package com.lesso.newlp.api.v1.home.controller;

import com.lesso.newlp.auth.model.CurrentUser;
import com.lesso.newlp.home.entity.PanelEntity;
import com.lesso.newlp.home.repository.PanelRepository;
import com.lesso.newlp.home.service.HomeService;
import com.lesso.newlp.home.service.PanelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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
    HomeService service;
    @Resource
    PanelService panelService;

    @Resource
    PanelRepository panelRepository;

//    @RequestMapping
//    public ResponseEntity<Model> render(Model model, @AuthenticationPrincipal User user) {
//        java.util.List<PanelEntity> panelEntity = panelRepository.findAll();
//        return new ResponseEntity<>(service.getModel(model,user), HttpStatus.OK);
//    }

    @RequestMapping("/panels")
    public HttpEntity<PagedResources<PanelEntity>> get(Model model, Pageable pageable, PagedResourcesAssembler assembler,@CurrentUser User user) {
        Page<PanelEntity> panels = panelService.findByMemberId(user.getUsername(),pageable);
        return new ResponseEntity<>(assembler.toResource(panels), HttpStatus.OK);
    }
}
