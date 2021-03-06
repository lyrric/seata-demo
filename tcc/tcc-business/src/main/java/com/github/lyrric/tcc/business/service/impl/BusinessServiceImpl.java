package com.github.lyrric.tcc.business.service.impl;

import com.github.lyrric.tcc.business.action.AccountAction;
import com.github.lyrric.tcc.business.action.OrderAction;
import com.github.lyrric.tcc.business.model.BusinessException;
import com.github.lyrric.tcc.business.service.BusinessService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private AccountAction accountAction;
    @Resource
    private OrderAction orderAction;

    @Override
    @GlobalTransactional
    public void trade() {
        final String username = "zhangshan";
        final Integer money = new Random().nextInt(50);
        accountAction.payPre(null, username, money);
        orderAction.preOrder(null, username, money);
    }
}
