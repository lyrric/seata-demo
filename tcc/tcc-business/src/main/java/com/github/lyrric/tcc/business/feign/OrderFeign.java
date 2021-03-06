package com.github.lyrric.tcc.business.feign;


import com.github.lyrric.tcc.business.model.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("seata-tcc-order")
public interface OrderFeign {

    /**
     * 预付款
     * @param xid
     * @param username 用户名
     * @param money 预付款金额
     */
    @GetMapping(value = "/pre/order")
    HttpResult orderPre(@RequestParam("xid") String xid,
                        @RequestParam("username") String username,
                        @RequestParam("money") Integer money);

    @GetMapping(value = "/commit")
    HttpResult commit(@RequestParam("xid")String xid);

    @GetMapping(value = "/rollback")
    HttpResult rollback(@RequestParam("xid")String xid);
}
