package com.github.lyrric.tcc.account.my.test;


import com.github.lyrric.tcc.business.BusinessApplication;
import com.github.lyrric.tcc.business.service.BusinessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITest {

    @Resource
    private BusinessService businessService;

    @Test
    public void DaoTest(){
        businessService.trade();
    }

}
