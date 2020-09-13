package com.github.lyrric.tcc.business.action.impl;

import com.github.lyrric.tcc.business.action.AccountAction;
import com.github.lyrric.tcc.business.feign.AccountFeign;
import com.github.lyrric.tcc.business.feign.OrderFeign;
import com.github.lyrric.tcc.business.model.BusinessException;
import com.github.lyrric.tcc.business.model.HttpResult;
import com.github.lyrric.tcc.common.UnknownException;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AccountActionImpl implements AccountAction {

    @Resource
    private AccountFeign accountFeign;
    @Resource
    private OrderFeign oderFeign;

    /**
     * 预付款
     *
     * @param actionContext
     * @param username        用户ID
     * @param money         预付款金额
     */
    @Override
    public HttpResult payPre(BusinessActionContext actionContext, String username, Integer money) {
        String xid = actionContext.getXid();
        log.info("invoke AccountAction invoke [xid={}]，[money={}]", xid, money);
        HttpResult httpResult = accountFeign.payPre(xid, username, money);
        checkResult(httpResult, xid);
        return httpResult;

    }

    @Override
    public HttpResult commit(BusinessActionContext context) {
        String xid = context.getXid();
        log.info("invoke AccountAction invoke [xid={}]", xid);
        HttpResult httpResult = accountFeign.commit(xid);
        checkResult(httpResult, xid);
        return httpResult;
    }

    @Override
    public HttpResult rollback(BusinessActionContext context) {
        String xid = context.getXid();
        log.info("invoke AccountAction invoke [xid={}", xid);
        HttpResult httpResult = accountFeign.rollback(xid);
        checkResult(httpResult, xid);
        return httpResult;
    }

    private void checkResult(HttpResult httpResult, String xid){
        if(!httpResult.getSuccess()){
            if(httpResult.getCode().equals(HttpResult.BUSINESS_EXCEPTION_CODE)){
                log.info("发生业务异常[xid={}]", xid);
                throw new BusinessException("业务异常："+httpResult.getErrMsg());
            }else{
                log.info("发生其他异常[xid={}]", xid);
                throw new UnknownException("随机异常");
            }
        }
    }
}