package com.github.lyrric.at.business.feign;

import com.github.lyrric.at.business.model.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "seata-at-account")
public interface AccountFeign {
    /**
     * 预付款
     * @param xid
     * @param username 用户名
     * @param money 预付款金额
     */
    @GetMapping(value = "/pay")
    HttpResult pay(@RequestParam("xid") String xid,
                      @RequestParam("username") String username,
                      @RequestParam("money") Integer money);
}
