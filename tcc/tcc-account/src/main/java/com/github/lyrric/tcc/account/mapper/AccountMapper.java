package com.github.lyrric.tcc.account.mapper;

import com.github.lyrric.tcc.account.core.BaseMapper;
import com.github.lyrric.tcc.account.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountMapper extends BaseMapper<Account> {


    @Select("select * from account where username = #{username} for update")
    @ResultMap("BaseResultMap")
    Account selectForUpdate(@Param("username")String username);


    @Update("update account set balance = balance - #{money} where username = #{username}")
    void prePay(@Param("username") String username,
               @Param("money") int money);
}