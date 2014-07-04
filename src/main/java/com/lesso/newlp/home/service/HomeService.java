package com.lesso.newlp.home.service;

import com.lesso.newlp.home.dto.PanelDTO;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 5:21 PM
 */
public interface HomeService {

    List<PanelDTO> getPanels(User user);

}
