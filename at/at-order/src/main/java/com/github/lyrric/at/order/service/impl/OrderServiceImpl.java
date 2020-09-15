package com.github.lyrric.at.order.service.impl;

import com.github.lyrric.at.order.entity.InvokeRecord;
import com.github.lyrric.at.order.entity.Orders;
import com.github.lyrric.at.order.mapper.InvokeRecordMapper;
import com.github.lyrric.at.order.mapper.OrderMapper;
import com.github.lyrric.at.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.weekend.Weekend;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private InvokeRecordMapper invokeRecordMapper;


    private static final String ORDER = "ORDER";

    /**
     * 预下单
     *
     * @param xid
     * @param username
     * @param money
     */
    @Override
    //@GlobalTransactional
    public void order(String xid, String username, Integer money) {
        if(hasBeenInvoked(xid, ORDER)){
            return;
        }
        Orders orders = new Orders();
        orders.setCreateTime(new Date());
        orders.setMoney(money);
        orders.setXid(xid);
        orders.setStatus(0);
        orderMapper.insert(orders);
        saveInvokeRecord(xid, ORDER);
        //随机测试回滚情况
        //throw new BusinessException("preOrder测试回滚失败");
//        if(new Random().nextBoolean()){
//            throw new BusinessException("随机回滚");
//        }
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
