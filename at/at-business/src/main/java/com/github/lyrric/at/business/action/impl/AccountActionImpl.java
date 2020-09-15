package com.github.lyrric.at.business.action.impl;

import com.github.lyrric.at.business.action.AccountAction;
import com.github.lyrric.at.business.feign.AccountFeign;
import com.github.lyrric.at.business.model.BusinessException;
import com.github.lyrric.at.business.model.HttpResult;
import com.github.lyrric.at.common.OtherException;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AccountActionImpl implements AccountAction {

    @Resource
    private AccountFeign accountFeign;

    /**
     * 预付款
     *
     * @param xid
     * @param username        用户ID
     * @param money         预付款金额
     */
    @Override
    public boolean pay(String xid , String username, Integer money) {
        log.info("invoke AccountAction payPre [xid={}]，[money={}]", xid, money);
        HttpResult httpResult = accountFeign.pay(xid, username, money);
        if(!httpResult.getSuccess()){
            throw new BusinessException("业务异常："+httpResult.getErrMsg());
        }
        return true;
    }
}
