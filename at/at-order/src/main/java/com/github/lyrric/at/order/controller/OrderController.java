package com.github.lyrric.at.order.controller;

import com.github.lyrric.at.order.service.OrderService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping(value = "/order")
    void order(String username, Integer money)  {
        String xid = RootContext.getXID();
        log.info("root content xid = {}", xid);
        orderService.order(xid, username, money);
    }


}
