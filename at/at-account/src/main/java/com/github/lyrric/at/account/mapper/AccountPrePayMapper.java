package com.github.lyrric.at.account.mapper;

import com.github.lyrric.at.account.core.BaseMapper;
import com.github.lyrric.at.account.entity.AccountPrePay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface AccountPrePayMapper extends BaseMapper<AccountPrePay> {

    @Select("select count(1) from (select 1 from account_pre_pay where xid = #{xid}) t")
    int existsWithXid(@Param("xid") String xid);

    @Select("select * from account_pre_pay where xid = #{xid} for update")
    @ResultMap("BaseResultMap")
    AccountPrePay selectByXidForUpdate(@Param("xid") String xid);
}