package com.baiyx.wfwbitest.dao;

import com.baiyx.wfwbitest.entity.TokenAccess;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author: 白宇鑫
 * @Date: 2022-10-12 下午 04:39
 * @Description: 查询权限DAO层
 */

@Mapper
public interface TokenAccessDao {

    // 查询用户权限状态
    Integer checkStatus(TokenAccess tokenAccess);
}
