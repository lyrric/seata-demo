package com.github.lyrric.tcc.account.service;

import com.github.lyrric.tcc.account.model.BusinessException;

public interface AccountService {

    /**
     * 预付款
     * @param xid
     * @param username
     * @param money
     */
    void prePay(String xid, String username, Integer money) throws BusinessException;

    /**
     *
     * @param xid
     */
    void commit(String xid);

    /**
     * 回滚
     * @param xid
     */
    void rollback(String xid);
}
