package com.baiyx.wfwbitest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-13 上午 11:33
 * @Description: 读取用户自定义配置的key
 */
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {
    //默认的key
    private final static String DEFAULT_KEY = "@bAiTestKey.!*%@";

    private String key = DEFAULT_KEY;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
