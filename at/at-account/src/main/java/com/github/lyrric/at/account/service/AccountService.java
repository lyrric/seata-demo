package com.github.lyrric.at.account.service;

public interface AccountService {

    /**
     * 预付款
     * @param xid
     * @param username
     * @param money
     */
    void pay(String xid, String username, Integer money)  ;

}
