package com.github.lyrric.tcc.order.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "invoke_record")
public class InvokeRecord extends BaseEntity {
    /**
     * 事务ID
     */
    private String xid;

    /**
     * 操作（格式：PAY_PRE、PAY_COMMIT、PAY_ROLLBACK）
     */
    private String function;

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
     * 获取操作（格式：PAY_PRE、PAY_COMMIT、PAY_ROLLBACK）
     *
     * @return function - 操作（格式：PAY_PRE、PAY_COMMIT、PAY_ROLLBACK）
     */
    public String getFunction() {
        return function;
    }

    /**
     * 设置操作（格式：PAY_PRE、PAY_COMMIT、PAY_ROLLBACK）
     *
     * @param function 操作（格式：PAY_PRE、PAY_COMMIT、PAY_ROLLBACK）
     */
    public void setFunction(String function) {
        this.function = function == null ? null : function.trim();
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