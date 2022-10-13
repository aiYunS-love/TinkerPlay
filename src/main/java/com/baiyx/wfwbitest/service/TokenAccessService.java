package com.baiyx.wfwbitest.service;

import com.baiyx.wfwbitest.entity.TokenAccess;

import java.util.Map;

/**
 * @Author: 白宇鑫
 * @Date: 2022-10-12 下午 04:46
 * @Description: 权限验证service层
 */


public interface TokenAccessService {

    // 查询用户权限状态
    int checkStatus(TokenAccess tokenAccess);
}
