package com.github.lyrric.tcc.account.service.impl;

import com.github.lyrric.tcc.account.entity.Account;
import com.github.lyrric.tcc.account.entity.AccountPrePay;
import com.github.lyrric.tcc.account.entity.InvokeRecord;
import com.github.lyrric.tcc.account.mapper.AccountMapper;
import com.github.lyrric.tcc.account.mapper.AccountPrePayMapper;
import com.github.lyrric.tcc.account.mapper.InvokeRecordMapper;
import com.github.lyrric.tcc.account.model.BusinessException;
import com.github.lyrric.tcc.account.service.AccountService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private static final String PAY_PRE = "PAY_PRE";
    private static final String PAY_COMMIT = "PAY_COMMIT";
    private static final String PAY_ROLLBACK = "PAY_ROLLBACK";

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void prePay(String xid, String username, Integer money) {
        //接口幂等性实现
        if(hasBeenInvoked(xid, PAY_PRE)){
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
        //这里利用了account_pre_pay表的xid唯一索引，解决了并发问题
        accountPrePayMapper.insert(accountPrePay);
        //利用invoke_record的联合索引也可以
        InvokeRecord record = new InvokeRecord();
        record.setXid(xid);
        record.setFunction(PAY_PRE);
        record.setCreateTime(new Date());
        invokeRecordMapper.insert(record);
    }

    /**
     * @param xid
     */
    @Override
    public void commit(String xid) {
        //因为钱已经被预扣除了，所以记录下被调用即可
        if(!hasBeenInvoked(xid, PAY_COMMIT)){
            saveInvokeRecord(xid, PAY_COMMIT);
        }
    }

    /**
     * 回滚
     * @param xid
     */
    @Override
    @Transactional
    public void rollback(String xid) {
        //接口幂等性实现
        if(hasBeenInvoked(xid, PAY_PRE)){
            return;
        }
        //获取预付款数据
        Weekend<AccountPrePay> weekend = new Weekend<>(AccountPrePay.class);
        weekend.weekendCriteria()
                .andEqualTo(AccountPrePay::getXid, xid);
        AccountPrePay accountPrePay = accountPrePayMapper.selectOneByExample(weekend);
        if(accountPrePay != null){
            //锁住用户
            Account account = accountMapper.selectForUpdate(accountPrePay.getUsername());
            account.setBalance(account.getBalance()+accountPrePay.getMoney());
            accountMapper.updateByPrimaryKey(account);
        }
        //记录执行
        saveInvokeRecord(xid, PAY_ROLLBACK);
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
