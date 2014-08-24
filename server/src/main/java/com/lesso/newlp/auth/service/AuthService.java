package com.lesso.newlp.auth.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Created by Sean on 8/12/2014.
 */
public interface AuthService {
    List<GrantedAuthority> getGrantedAuthority(String username);
}
