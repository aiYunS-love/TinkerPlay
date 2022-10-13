package com.baiyx.wfwbitest.service;

import com.baiyx.wfwbitest.entity.*;
import com.baiyx.wfwbitest.utils.RowConvertColUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @Author: 白宇鑫
 * @Date: 2021/6/30 上午 11:30
 * @Description: Service层
 */
public interface UserService {

    /**
    * @Author: 白宇鑫
    * @Description: 查
    * @Date: 2021/6/30 上午 11:31
    * @Param:
    * @return: java.util.List<com.baiyx.wfwbitest.entity.user>
    */
    List<User> findAll();

    // 增
    R insertOne(User user);

    // 删
    void deleteByName(QueryRequestVo queryRequestVo);

    // 改
    void updateOne(QueryRequestVo queryRequestVo);

    // 根据id查
    R findById(QueryRequestVo queryRequestVo);

    // 根据name查
    User findByName(QueryRequestVo queryRequestVo);

    // 根据时间区间查询
    List<User> findByTime(QueryRequestVo queryRequestVo);

    // 行转列
    RowConvertColUtil.ConvertData RowConvertCol(QueryRequestVo queryRequestVo);

    // 批量插入
    void insertAll(List<User> userList);

    // 获取IP或MAC地址
    Map getIPorMACaddress(HttpServletRequest request) throws UnknownHostException, Exception;

    // 消除转义
    ResultMsg removeESC();

    // 批量查询
    // List findByprojbase(List<Projbase> projbase);
}
