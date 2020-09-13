package com.github.lyrric.tcc.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
public class BusinessController {


    @GetMapping(value = "/test")
    String index(int id){
        return "test";
    }


}
