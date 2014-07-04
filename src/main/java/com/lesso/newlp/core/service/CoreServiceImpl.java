package com.lesso.newlp.core.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDTO: Sean
 * Date: 7/3/13
 * Time: 4:01 AM
 */
@Service
public class CoreServiceImpl implements com.lesso.newlp.core.service.CoreService {


    public List findAll(){
        List<String> list = new ArrayList<String>();
        list.add(1,"ok");
        return list;
    }

}
