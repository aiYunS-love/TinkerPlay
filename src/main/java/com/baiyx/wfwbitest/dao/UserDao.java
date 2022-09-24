package com.baiyx.wfwbitest.dao;

import com.baiyx.wfwbitest.entity.Projbase;
import com.baiyx.wfwbitest.entity.User;
import com.baiyx.wfwbitest.util.RowConvertColUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2021/6/30 上午 11:28
 * @Description:
 */
@Mapper
@CacheConfig(cacheNames = "UserDao")
public interface UserDao {

    // 查
    // @Cacheable(cacheNames = "findAll",cacheManager = "cacheManager")
    @Cacheable(cacheManager = "cacheManager")
    List<User> findAll();

    // 增
    void insertOne(User user);

    // 删
    void deleteByName(String username);

    // 改
    void updateOne(User user);

    // 根据id查
    User findById(int id);

    // 根据name查
    @Cacheable
    User findByName(String username);

    // 根据时间区间查询
    // @Cacheable(value = "findByTime",cacheManager = "expire1min")
    @Cacheable(cacheManager = "expire1min")
    List<User> findByTime(Date startTime,Date endTime);

    // 行转列
    RowConvertColUtil RowConvertCol(Date startTime, Date endTime);

    // 批量插入
    void insertAll(List<User> userList);

    // 获取IP或MAC地址
    List<User> getIPorMACaddress(HttpServletRequest request);

    // 消除转义
    void removeESC(List<Projbase> projbase);
    // 批量查询
    ArrayList findByprojbase(ArrayList projbase);
}
