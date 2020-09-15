package com.github.lyrric.at.business.action;

public interface AccountAction {


    /**
     * 预付款
     * @param xid
     * @param username 用户名
     * @param money 预付款金额
     */
    boolean pay(String xid, String username, Integer money);

}
