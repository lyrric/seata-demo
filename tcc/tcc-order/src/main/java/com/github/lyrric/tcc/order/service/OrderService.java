package com.github.lyrric.tcc.order.service;

public interface OrderService {

    /**
     * 预下单
     * @param xid
     * @param username
     * @param money
     */
    void preOrder(String xid, String username, Integer money) ;

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
