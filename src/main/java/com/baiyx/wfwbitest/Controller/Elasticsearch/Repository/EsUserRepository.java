package com.baiyx.wfwbitest.Controller.Elasticsearch.Repository;

import com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @Author: baiyx
 * @Date: 2023年9月11日, 0011 下午 4:02:04
 * @Description: 派生计数和查询
 */
public interface EsUserRepository extends BaseRepository<EsUser, Long> {

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
     * @return: java.util.List<com.baiyx.wfwbitest.Entity.EsUser>
     */
    List<EsUser> removeByname(String name);

    /***
     * @Author: baiyx
     * @Description: 查询
     * @Date: 2023年9月11日, 0011 下午 4:36:50
     * @param name
     * @return: java.util.List<com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findByname(String name);

    /***
     * @Author: baiyx
     * @Description: 根据邮箱地址查询ES
     * @Date: 2023年9月11日, 0011 下午 5:06:03
     * @param EMAIL
     * @return: com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser
     */
    EsUser findByEMAIL(String EMAIL);

    /***
     * @Author: baiyx
     * @Description: 根据邮箱地址和姓名查询
     * @Date: 2023年9月11日, 0011 下午 5:15:47
     * @param EMAIL
     * @param name
     * @return: java.util.List<com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findByEMAILAndName(String EMAIL, String name);

    /***
     * @Author: baiyx
     * @Description: 为查询启用非重复标志
     * @Date: 2023年9月11日, 0011 下午 5:23:13
     * @param name
     * @return: java.util.List<com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findDistinctUserByname(String name);
    List<EsUser> findUserDistinctByname(String name);

    /***
     * @Author: baiyx
     * @Description: 为单个属性启用忽略大小写
     * @Date: 2023年9月11日, 0011 下午 5:28:25
     * @param name
     * @return: java.util.List<com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findBynameIgnoreCase(String name);

    /***
     * @Author: baiyx
     * @Description: 为所有合适的属性启用忽略大小写
     * @Date: 2023年9月11日, 0011 下午 5:32:08
     * @param name
     * @return: java.util.List<com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findBynameAllIgnoreCase(String name);

    /***
     * @Author: baiyx
     * @Description: 为查询启用静态 ORDER BY
     * @Date: 2023年9月11日, 0011 下午 5:36:55
     * @Param:
     * @param lastName
     * @return: java.util.List<com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser>
     */
    List<EsUser> findByLastNameOrderByFirstNameAsc(String lastName);
    List<EsUser> findByLastNameOrderByFirstNameDesc(String lastName);

    /***
     * @Author: baiyx
     * @Description: 异步查询
     * @Date: 2023年9月12日, 0012 下午 2:11:27
     * @param firstName
     * @return: java.util.concurrent.Future<com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser>
     */
    @Async
    Future<EsUser> findByFirstName(String firstName);
    @Async
    CompletableFuture<EsUser> findOneByFirstName(String firstName);
    @Async
    ListenableFuture<EsUser> findOneByLastName(String lastName);

    EsUser searchById(String id);

}
