package com.github.lyrric.at.account.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "account_pre_pay")
public class AccountPrePay extends BaseEntity {
    /**
     * 事务ID
     */
    private String xid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 预付款金额
     */
    private Integer money;

    /**
     * 状态（0=预扣除，1=commit，2=已回滚）
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取事务ID
     *
     * @return xid - 事务ID
     */
    public String getXid() {
        return xid;
    }

    /**
     * 设置事务ID
     *
     * @param xid 事务ID
     */
    public void setXid(String xid) {
        this.xid = xid == null ? null : xid.trim();
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取预付款金额
     *
     * @return money - 预付款金额
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * 设置预付款金额
     *
     * @param money 预付款金额
     */
    public void setMoney(Integer money) {
        this.money = money;
    }

    /**
     * 获取状态（0=预扣除，1=commit，2=已回滚）
     *
     * @return status - 状态（0=预扣除，1=commit，2=已回滚）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（0=预扣除，1=commit，2=已回滚）
     *
     * @param status 状态（0=预扣除，1=commit，2=已回滚）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}