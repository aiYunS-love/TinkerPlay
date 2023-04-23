package com.baiyx.wfwbitest.Controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 白宇鑫
 * @Date: 2023年3月25日, 0025 下午 5:18:56
 * @Description: Nacos配置中心Test
 */
@RestController
@RefreshScope // 动态刷新
@Api(tags = "NacosController", description = "Nacos")
@RequestMapping("/nacos")
public class NacosController {

    @Value("${config.info}")
    private String configInfo;

    @ApiOperation(value = "Nacos配置中心Test")
    @GetMapping("/getConfigInfo")
    public String getConfigInfo() {
        return configInfo;
    }

}
