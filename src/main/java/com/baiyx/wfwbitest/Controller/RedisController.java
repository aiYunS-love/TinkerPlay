package com.baiyx.wfwbitest.Controller;

import com.baiyx.wfwbitest.Common.RedisService;
import com.baiyx.wfwbitest.CustomAnnotations.WebLog;
import com.baiyx.wfwbitest.Entity.User;
import com.baiyx.wfwbitest.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2023年3月9日, 0009 下午 4:48:18
 * @Description: 测试操作redis Service
 */
@RestController
@RequestMapping(value ="RedisController",produces = "application/json;charset=UTF-8")
@EnableScheduling // 1.开启定时任务
@EnableAsync // 2.开启多线程
@Api(tags = "RedisController", description = "Redis操作模块")
public class RedisController {

    @Autowired
    RedisService redisService;

    @ApiOperation(value = "测试Redis_Service")
    @WebLog(description = "测试Redis_Service")
    @RequestMapping(value = "/redis", method= RequestMethod.GET)
    public void redis(){
        redisService.set("A","a");
        redisService.set("B","b",30);
        redisService.set("C","c");
        System.out.println(redisService.get("A"));
        redisService.expire("C",30);
        System.out.println(redisService.getExpire("C"));
        redisService.del("A");
    }
}
