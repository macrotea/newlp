package com.lesso.newlp.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserDTO: Sean
 * Date: 7/3/13
 * Time: 6:06 PM
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super();
        }
    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
        }
    public PageNotFoundException(String message) {
        super(message);
        }
    public PageNotFoundException(Throwable cause) {
        super(cause);
        }
}