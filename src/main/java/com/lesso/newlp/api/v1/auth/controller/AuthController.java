package com.lesso.newlp.api.v1.auth.controller;

import com.google.common.base.Strings;
import com.lesso.newlp.auth.model.CurrentUser;
import com.lesso.newlp.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

/**
 * UserDTO: sean
 * Date: 9/25/13
 * Time: 11:29 AM
 */

@Controller
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

//    Logger logger = LogManager.getLogger();

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    AuthService authService;

    @Resource
    UserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    public ResponseEntity<User> authenticate(HttpServletRequest request,String username, String password,@CurrentUser User user) {

        ResponseEntity<User> entity = new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);

        if(null != user){
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            UsernamePasswordAuthenticationToken  token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());

            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = authenticationManager.authenticate(token);
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                entity = new ResponseEntity<User>((User) authentication.getPrincipal(),HttpStatus.OK);
            }
        }

        return entity;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<User> authenticate(HttpServletRequest request,String username, String password) {
//        logger.debug("authenticating : {}",username);
        ResponseEntity<User> entity = null;
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            entity = new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        } else {
            try {
                UsernamePasswordAuthenticationToken  token = new UsernamePasswordAuthenticationToken(username, password);
                // generate session if one doesn't exist
                request.getSession();

                token.setDetails(new WebAuthenticationDetails(request));
                Authentication authentication = authenticationManager.authenticate(token);
                if (authentication.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    entity = new ResponseEntity<User>((User) authentication.getPrincipal(),HttpStatus.OK);
                }
            } catch (Exception e) {
                entity = new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
            }
        }

        return entity;
    }

}
