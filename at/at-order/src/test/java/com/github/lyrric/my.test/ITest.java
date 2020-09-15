package com.github.lyrric.my.test;


import org.junit.Test;
import com.github.lyrric.at.order.AtOrderApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtOrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITest {

    @Test
    public void DaoTest(){

    }

}
