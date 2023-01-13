package com.baiyx.wfwbitest.Entity;

import lombok.Data;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-15 上午 10:08
 * @Description: 客户端信息
 */
@Data
public class ClientMsg {

    private String ClientType;
    private String SystemType;
    private String BrowserType;

    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType) {
        ClientType = clientType;
    }

    public String getSystemType() {
        return SystemType;
    }

    public void setSystemType(String systemType) {
        SystemType = systemType;
    }

    public String getBrowserType() {
        return BrowserType;
    }

    public void setBrowserType(String browserType) {
        BrowserType = browserType;
    }
}
