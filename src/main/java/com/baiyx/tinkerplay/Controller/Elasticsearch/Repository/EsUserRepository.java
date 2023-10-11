package com.baiyx.tinkerplay.Controller.Elasticsearch.Repository;

import com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @Author: baiyx
 * @Date: 2023年9月11日, 0011 下午 4:02:04
 * @Description: 派生计数和查询
 */
public interface EsUserRepository extends ElasticsearchRepository<EsUser, Long> {

    /***
     * @Author: baiyx
     * @Description: 派生计数查询
     * @Date: 2023年9月11日, 0011 下午 4:04:39
     * @param name
     * @return: long
     */
    long countByname(String name);

    /***
     * @Author: baiyx
     * @Description: 派生删除查询
     * @Date: 2023年9月11日, 0011 下午 4:04:59
     * @param name
     * @return: long
     */
    long deleteByname(String name);

    /***
     * @Author: baiyx
     * @Description: 派生删除查询
     * @Date: 2023年9月11日, 0011 下午 4:05:23
     * @param name
     * @return: java.util.List<com.baiyx.tinkerplay.Entity.EsUser>
     */
    List<EsUser> removeByname(String name);

    /***
     * @Author: baiyx
     * @Description: 查询
     * @Date: 2023年9月11日, 0011 下午 4:36:50
     * @param name
     * @return: java.util.List<com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findByname(String name);

    /***
     * @Author: baiyx
     * @Description: 根据邮箱地址查询ES
     * @Date: 2023年9月11日, 0011 下午 5:06:03
     * @param EMAIL
     * @return: com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser
     */
    EsUser findByEMAIL(String EMAIL);

    /***
     * @Author: baiyx
     * @Description: 根据邮箱地址和姓名查询
     * @Date: 2023年9月11日, 0011 下午 5:15:47
     * @param EMAIL
     * @param name
     * @return: java.util.List<com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findByEMAILAndName(String EMAIL, String name);

    /***
     * @Author: baiyx
     * @Description: 为查询启用非重复标志
     * @Date: 2023年9月11日, 0011 下午 5:23:13
     * @param name
     * @return: java.util.List<com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findDistinctUserByname(String name);
    List<EsUser> findUserDistinctByname(String name);

    /***
     * @Author: baiyx
     * @Description: 为单个属性启用忽略大小写
     * @Date: 2023年9月11日, 0011 下午 5:28:25
     * @param name
     * @return: java.util.List<com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findBynameIgnoreCase(String name);

    /***
     * @Author: baiyx
     * @Description: 为所有合适的属性启用忽略大小写
     * @Date: 2023年9月11日, 0011 下午 5:32:08
     * @param name
     * @return: java.util.List<com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findBynameAllIgnoreCase(String name);

    /***
     * @Author: baiyx
     * @Description: 为查询启用静态 ORDER BY
     * @Date: 2023年9月11日, 0011 下午 5:36:55
     * @Param:
     * @param lastName
     * @return: java.util.List<com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findByLastNameOrderByFirstNameAsc(String lastName);
    List<EsUser> findByLastNameOrderByFirstNameDesc(String lastName);

    /***
     * @Author: baiyx
     * @Description: 异步查询
     * @Date: 2023年9月12日, 0012 下午 2:11:27
     * @param firstName
     * @return: java.util.concurrent.Future<com.baiyx.tinkerplay.Controller.Elasticsearch.EsEntity.EsUser>
     */
    @Async
    Future<EsUser> findByFirstName(String firstName);
    @Async
    CompletableFuture<EsUser> findOneByFirstName(String firstName);
    @Async
    ListenableFuture<EsUser> findOneByLastName(String lastName);

    /**
     * 根据性别 搜索查询
     */
    Page<EsUser> findBySex(String sex, Pageable page);

    /**
     * 搜索查询
     * @param name             姓名
     * @param sex              性别
     * @param address          地址
     * @param EMAIL            邮箱
     * @param CARD             证件号
     * @param PHONE            电话
     * @param keywords         关键字
     */
    Page<EsUser> findByKeywords(String name, String sex, String address, String EMAIL, String CARD, String PHONE, String keywords, Pageable page);

    /**
     * 根据姓名或关键字 搜索查询
     */
    Page<EsUser> findByNameOrKeywords(String name, String keywords, Pageable page);

    /**
     * 根据姓名或证件号或关键字 搜索查询
     */
    Page<EsUser> findByNameOrCARDOrKeywords(String name, String CARD, String keywords, Pageable page);

}
