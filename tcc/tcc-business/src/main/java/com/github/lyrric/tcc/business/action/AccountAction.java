package com.github.lyrric.tcc.business.action;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface AccountAction {


    /**
     * 预付款
     * @param actionContext
     * @param username 用户名
     * @param money 预付款金额
     */
    @TwoPhaseBusinessAction(name = "account_pay", commitMethod = "commit", rollbackMethod = "rollback")
    boolean payPre(BusinessActionContext actionContext, String username, Integer money);

    boolean commit(BusinessActionContext context);

    boolean rollback(BusinessActionContext context);
}
