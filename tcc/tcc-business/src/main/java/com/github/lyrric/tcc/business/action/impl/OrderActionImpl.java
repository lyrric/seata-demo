package com.github.lyrric.tcc.business.action.impl;

import com.github.lyrric.tcc.business.action.OrderAction;
import com.github.lyrric.tcc.business.feign.OrderFeign;
import com.github.lyrric.tcc.business.model.BusinessException;
import com.github.lyrric.tcc.business.model.HttpResult;
import com.github.lyrric.tcc.common.NoRollbackException;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class OrderActionImpl implements OrderAction {

    @Resource
    private OrderFeign orderFeign;

    @Override
    public boolean preOrder(BusinessActionContext actionContext, String username, Integer money) {
        String xid = actionContext.getXid();
        log.info("invoke orderAction preOrder [xid={}]，[money={}]", xid, money);
        HttpResult httpResult = orderFeign.orderPre(xid, username, money);
        if(!httpResult.getSuccess()){
            throw new BusinessException("业务异常："+httpResult.getErrMsg());
        }
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext context) {
        String xid = context.getXid();
        log.info("invoke orderAction commit [xid={}]", xid);
        HttpResult httpResult = orderFeign.commit(xid);
        checkResult(httpResult, xid);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext context) {
        String xid = context.getXid();
        log.info("invoke orderAction rollback  [xid={}]", xid);
        HttpResult httpResult = orderFeign.rollback(xid);
        checkResult(httpResult, xid);
        return true;
    }

    private void checkResult(HttpResult httpResult, String xid){
        if(!httpResult.getSuccess()){
            if(httpResult.getCode().equals(HttpResult.BUSINESS_EXCEPTION_CODE)){
                log.info("发生业务异常[xid={}]", xid);
                throw new BusinessException("业务异常："+httpResult.getErrMsg());
            }else{
                log.info("发生其他异常[xid={}]", xid);
                throw new NoRollbackException("其它异常："+httpResult.getErrMsg());
            }
        }
    }
}
