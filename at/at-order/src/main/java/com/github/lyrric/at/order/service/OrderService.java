package com.github.lyrric.at.order.service;

public interface OrderService {

    /**
     * 预下单
     * @param xid
     * @param username
     * @param money
     */
    void order(String xid, String username, Integer money) ;

}
