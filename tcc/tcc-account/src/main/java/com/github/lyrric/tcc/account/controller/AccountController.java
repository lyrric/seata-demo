package com.github.lyrric.tcc.account.controller;

import com.github.lyrric.tcc.account.model.BusinessException;
import com.github.lyrric.tcc.account.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @GetMapping(value = "/pre/pay")
    void prePay(String xid, String username, Integer money)  {
        accountService.prePay(xid, username, money);
    }

    @GetMapping(value = "/commit")
    void commit(String xid){
        accountService.commit(xid);
    }
    @GetMapping(value = "/rollback")
    public void rollback(String xid){
        accountService.rollback(xid);
    }

}
