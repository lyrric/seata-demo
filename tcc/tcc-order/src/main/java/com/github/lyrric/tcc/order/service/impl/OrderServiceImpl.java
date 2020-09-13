package com.github.lyrric.tcc.order.service.impl;

import com.github.lyrric.tcc.order.entity.InvokeRecord;
import com.github.lyrric.tcc.order.entity.Order;
import com.github.lyrric.tcc.order.mapper.InvokeRecordMapper;
import com.github.lyrric.tcc.order.mapper.OrderMapper;
import com.github.lyrric.tcc.order.service.OrderService;
import org.bouncycastle.asn1.esf.OtherRevRefs;
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
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setMoney(money);
        order.setXid(xid);
        order.setStatus(0);
        orderMapper.insert(order);
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
        Weekend<Order> weekend = new Weekend<>(Order.class);
        weekend.weekendCriteria()
                .andEqualTo(Order::getXid, xid);
        Order order = new Order();
        order.setStatus(1);
        orderMapper.updateByExampleSelective(order, weekend);
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
        Weekend<Order> weekend = new Weekend<>(Order.class);
        weekend.weekendCriteria()
                .andEqualTo(Order::getXid, xid);
        orderMapper.deleteByExample(weekend);
        saveInvokeRecord(xid, ORDER_ROLLBACK);
    }

    private boolean hasBeenInvoked(String xid, String function){
        Weekend<InvokeRecord> weekend = new Weekend<>(InvokeRecord.class);
        weekend.weekendCriteria()
                .andEqualTo(InvokeRecord::getXid, xid)
                .andEqualTo(InvokeRecord::getFunction, function);
        return invokeRecordMapper.selectCountByExample(function) > 0;
    }

    private void saveInvokeRecord(String xid, String function){
        InvokeRecord record = new InvokeRecord();
        record.setXid(xid);
        record.setFunction(function);
        record.setCreateTime(new Date());
        invokeRecordMapper.insert(record);
    }
}
