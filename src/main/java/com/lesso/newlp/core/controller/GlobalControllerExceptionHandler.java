package com.lesso.newlp.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * UserDTO: Sean
 * Date: 7/3/13
 * Time: 5:37 PM
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(com.lesso.newlp.core.controller.PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected String handlePageNotFoundException(RuntimeException ex, WebRequest request) {
        return "404";
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity handleMethodArgumentNotValidException( MethodArgumentNotValidException error ) {
//        ResponseEntity entity = new ResponseEntity<>(error.getBindingResult(), HttpStatus.OK);
//        return entity;
//    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView resolveBindingException ( BindException bindException, Locale locale){
        ModelAndView model = new ModelAndView();
        BindingResult bindingResult = bindException.getBindingResult();
        model.addObject("errors",bindingResult.getFieldErrors());
        return model;
    }
}