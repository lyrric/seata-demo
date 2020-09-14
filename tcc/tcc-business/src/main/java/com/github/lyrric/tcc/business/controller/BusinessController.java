package com.github.lyrric.tcc.business.controller;

import com.github.lyrric.tcc.business.service.BusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @GetMapping(value = "/trade")
    void index(){
        businessService.trade();
    }

}
