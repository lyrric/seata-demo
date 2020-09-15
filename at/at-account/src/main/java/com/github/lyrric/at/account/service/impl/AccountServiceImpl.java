package com.github.lyrric.at.account.service.impl;

import com.github.lyrric.at.account.entity.Account;
import com.github.lyrric.at.account.entity.AccountPrePay;
import com.github.lyrric.at.account.entity.InvokeRecord;
import com.github.lyrric.at.account.mapper.AccountMapper;
import com.github.lyrric.at.account.mapper.AccountPrePayMapper;
import com.github.lyrric.at.account.mapper.InvokeRecordMapper;
import com.github.lyrric.at.account.model.BusinessException;
import com.github.lyrric.at.account.service.AccountService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.weekend.Weekend;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private AccountPrePayMapper accountPrePayMapper;
    @Resource
    private InvokeRecordMapper invokeRecordMapper;

    private static final String PAY = "PAY";

    @Override
    //@Transactional(rollbackFor = RuntimeException.class)
    //@GlobalTransactional
    public void pay(String xid, String username, Integer money) {
        //接口幂等性实现
        if(hasBeenInvoked(xid, PAY)){
            return;
        }
        //锁住用户
        Account account = accountMapper.selectForUpdate(username);
        if(account.getBalance() < money){
            throw new BusinessException("用户余额不足");
        }
        //预付款，扣除金额
        accountMapper.prePay(username, money);
        //保存预付款记录
        AccountPrePay accountPrePay = new AccountPrePay();
        accountPrePay.setCreateTime(new Date());
        accountPrePay.setMoney(money);
        accountPrePay.setUsername(username);
        accountPrePay.setXid(xid);
        accountPrePay.setStatus(0);
        //这里利用了account_pre_pay表的xid唯一索引，解决了并发问题
        accountPrePayMapper.insert(accountPrePay);
        //利用invoke_record的联合索引也可以
        InvokeRecord record = new InvokeRecord();
        record.setXid(xid);
        record.setFunction(PAY);
        record.setCreateTime(new Date());
        invokeRecordMapper.insert(record);
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
