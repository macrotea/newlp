package com.lesso.newlp.home.dto;

import java.util.List;

/**
 * UserDTO: Sean
 * Date: 11/13/13
 * Time: 9:10 PM
 */
public class HomeVO {
    List<PanelDTO> panelDTOs;

    public List<PanelDTO> getPanels() {
        return panelDTOs;
    }

    public void setPanels(List<PanelDTO> panelDTOs) {
        this.panelDTOs = panelDTOs;
    }
}
