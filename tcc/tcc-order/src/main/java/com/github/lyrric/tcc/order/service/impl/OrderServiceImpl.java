package com.github.lyrric.tcc.order.service.impl;

import com.github.lyrric.tcc.order.entity.InvokeRecord;
import com.github.lyrric.tcc.order.entity.Orders;
import com.github.lyrric.tcc.order.mapper.InvokeRecordMapper;
import com.github.lyrric.tcc.order.mapper.OrderMapper;
import com.github.lyrric.tcc.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.weekend.Weekend;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private InvokeRecordMapper invokeRecordMapper;


    private static final String ORDER_PRE = "ORDER_PRE";
    private static final String ORDER_COMMIT = "ORDER_COMMIT";
    private static final String ORDER_ROLLBACK = "ORDER_ROLLBACK";

    /**
     * 预下单
     *
     * @param xid
     * @param username
     * @param money
     */
    @Override
    @Transactional
    public void preOrder(String xid, String username, Integer money) {
        if(hasBeenInvoked(xid, ORDER_PRE)){
            return;
        }
        Orders orders = new Orders();
        orders.setCreateTime(new Date());
        orders.setMoney(money);
        orders.setXid(xid);
        orders.setStatus(0);
        orderMapper.insert(orders);
        saveInvokeRecord(xid, ORDER_PRE);
    }

    /**
     * @param xid
     */
    @Override
    @Transactional
    public void commit(String xid) {
        if(hasBeenInvoked(xid, ORDER_COMMIT)){
            return;
        }
        Weekend<Orders> weekend = new Weekend<>(Orders.class);
        weekend.weekendCriteria()
                .andEqualTo(Orders::getXid, xid);
        Orders orders = new Orders();
        orders.setStatus(1);
        orderMapper.updateByExampleSelective(orders, weekend);
        saveInvokeRecord(xid, ORDER_COMMIT);
    }

    /**
     * 回滚
     *
     * @param xid
     */
    @Override
    @Transactional
    public void rollback(String xid) {
        if(hasBeenInvoked(xid, ORDER_ROLLBACK)){
            return;
        }
        Weekend<Orders> weekend = new Weekend<>(Orders.class);
        weekend.weekendCriteria()
                .andEqualTo(Orders::getXid, xid);
        orderMapper.deleteByExample(weekend);
        saveInvokeRecord(xid, ORDER_ROLLBACK);
    }

    private boolean hasBeenInvoked(String xid, String function){
        Weekend<InvokeRecord> weekend = new Weekend<>(InvokeRecord.class);
        weekend.weekendCriteria()
                .andEqualTo(InvokeRecord::getXid, xid)
                .andEqualTo(InvokeRecord::getFunction, function);
        return invokeRecordMapper.selectCountByExample(weekend) > 0;
    }

    private void saveInvokeRecord(String xid, String function){
        InvokeRecord record = new InvokeRecord();
        record.setXid(xid);
        record.setFunction(function);
        record.setCreateTime(new Date());
        invokeRecordMapper.insert(record);
    }
}
