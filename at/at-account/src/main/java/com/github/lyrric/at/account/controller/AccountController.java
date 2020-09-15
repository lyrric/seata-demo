package com.github.lyrric.at.account.controller;

import com.github.lyrric.at.account.service.AccountService;
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
public class AccountController {

    @Resource
    private AccountService accountService;

    @GetMapping(value = "/pay")
    void pay(String username, Integer money)  {
        String xid = RootContext.getXID();
        log.info("root content xid = {}", xid);
        accountService.pay(xid, username, money);
    }


}
