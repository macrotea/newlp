package com.lesso.newlp.core.controller;

import com.lesso.newlp.core.service.CoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * UserDTO: Sean
 * Date: 7/1/13
 * Time: 10:39 PM
 */
@Controller
public class GlobalController {

    @Resource
    CoreService coreService;

    @RequestMapping("/")
    public String handle(HttpServletRequest request,Model model){
        Locale locale = new Locale("zh","CN");
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);

        return "index";
    }

    @RequestMapping("/browser")
    public String handleIEAlert(HttpServletRequest request,Model model){
        Locale locale = new Locale("zh","CN");
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);

        return "browser";
    }


    @RequestMapping("/mobile")
    public String handleMobile(HttpServletRequest request,Model model){
        Locale locale = new Locale("zh","CN");
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);

        return "mobile";
    }
}
