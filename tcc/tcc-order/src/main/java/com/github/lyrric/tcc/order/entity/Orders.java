package com.github.lyrric.tcc.order.entity;

import java.util.Date;
import javax.persistence.*;

public class Orders extends BaseEntity {
    /**
     * 状态0=无效，，1=有效
     */
    private Integer status;

    /**
     * 订单金额
     */
    private Integer money;

    /**
     * 事务ID
     */
    private String xid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取状态0=无效，，1=有效
     *
     * @return status - 状态0=无效，，1=有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态0=无效，，1=有效
     *
     * @param status 状态0=无效，，1=有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取订单金额
     *
     * @return money - 订单金额
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * 设置订单金额
     *
     * @param money 订单金额
     */
    public void setMoney(Integer money) {
        this.money = money;
    }

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