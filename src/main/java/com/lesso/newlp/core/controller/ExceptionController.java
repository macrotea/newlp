package com.lesso.newlp.core.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * UserDTO: Sean
 * Date: 9/14/12
 * Time: 4:58 PM
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController {

    @Resource
    MessageSource messageSource;

    @RequestMapping("/MaxUploadSizeExceededException")
    @ResponseBody
    public Model handleMaxUploadSizeExceededException(Model model, HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        model.addAttribute("success",false);
        model.addAttribute("msg",messageSource.getMessage("20006", null, locale));
        return model;
    }
}
