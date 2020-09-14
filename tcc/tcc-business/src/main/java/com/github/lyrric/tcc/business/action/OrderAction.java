package com.github.lyrric.tcc.business.action;

import com.github.lyrric.tcc.business.model.HttpResult;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface OrderAction {


    @TwoPhaseBusinessAction(name = "order", commitMethod = "commit", rollbackMethod = "rollback")
    boolean preOrder(BusinessActionContext actionContext, String username, Integer money);

    boolean commit(BusinessActionContext context);

    boolean rollback(BusinessActionContext context);

}
