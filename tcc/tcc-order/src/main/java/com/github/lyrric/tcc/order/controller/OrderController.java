package com.github.lyrric.tcc.order.controller;

import com.github.lyrric.tcc.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping(value = "/pre/order")
    void prePay(String xid, String username, Integer money)  {
        orderService.preOrder(xid, username, money);
    }

    @GetMapping(value = "/commit")
    void commit(String xid){
        orderService.commit(xid);
    }

    @GetMapping(value = "/rollback")
    public void rollback(String xid){
        orderService.rollback(xid);
    }

}
