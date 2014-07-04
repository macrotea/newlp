package com.lesso.newlp.auth.controller;

import com.google.common.base.Strings;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * UserDTO: sean
 * Date: 9/25/13
 * Time: 11:29 AM
 */

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

//    Logger logger = LogManager.getLogger();

    @Resource
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<String> authenticate(HttpServletRequest request,String username, String password) {
//        logger.debug("authenticating : {}",username);
        ResponseEntity<String> entity = null;
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            entity = new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        } else {
            try {
                UsernamePasswordAuthenticationToken  token = new UsernamePasswordAuthenticationToken(username, password);
                // generate session if one doesn't exist
                request.getSession();

                token.setDetails(new WebAuthenticationDetails(request));
                Authentication authentication = authenticationManager.authenticate(token);
                if (authentication.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    entity = new ResponseEntity<String>(HttpStatus.OK);
                }
            } catch (Exception e) {
                entity = new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
            }
        }

        return entity;
    }

}
