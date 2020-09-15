package com.github.lyrric.at.business.action.impl;

import com.github.lyrric.at.business.action.OrderAction;
import com.github.lyrric.at.business.feign.OrderFeign;
import com.github.lyrric.at.business.model.BusinessException;
import com.github.lyrric.at.business.model.HttpResult;
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
    public boolean order(String xid, String username, Integer money) {
        log.info("invoke orderAction preOrder [xid={}]，[money={}]", xid, money);
        HttpResult httpResult = orderFeign.order(xid, username, money);
        if(!httpResult.getSuccess()){
            throw new BusinessException("业务异常："+httpResult.getErrMsg());
        }
        return true;
    }
}
