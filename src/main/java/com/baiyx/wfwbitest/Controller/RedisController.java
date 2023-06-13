package com.baiyx.wfwbitest.Controller;

import com.baiyx.wfwbitest.Common.CommonResult;
import com.baiyx.wfwbitest.Common.RedisService;
import com.baiyx.wfwbitest.CustomAnnotations.WebLog;
import com.baiyx.wfwbitest.Entity.Sms;
import com.baiyx.wfwbitest.Utils.SendSmsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

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

    @ApiOperation("测试获取验证码并发送到手机")
    @WebLog(description = "测试获取验证码并发送到手机")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CommonResult getAuthCode(@RequestBody Sms sms) {
        if(!redisService.hasKey(sms.getPhoneNumbers()[0])){
            // 生成验证码
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for(int i=0;i<6;i++){
                sb.append(random.nextInt(10));
            }
            String authCode = sb.toString();
            // 发送验证码
            String[] params = new String[1];
            params[0] = authCode;
            SendSmsUtil.sendSms(sms.getPhoneNumbers(),sms.getTemplateId(),sms.getSmsSign(),params);
            // 新起线程存到redis缓存,默认60s超时
            new Thread(() -> {
                redisService.set(sms.getPhoneNumbers()[0],sb,60);
            }).start();
            return CommonResult.success(authCode,"获取验证码成功");
        }
        return CommonResult.success(null,"请一分钟后重新获取验证码!");
    }
}
