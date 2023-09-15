package com.baiyx.wfwbitest.Controller.Elasticsearch.Repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * @Author: baiyx
 * @Date: 2023年9月11日, 0011 下午 5:01:04
 * @Description: ES的CRUD父接口
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {

    /***
     * @Author: baiyx
     * @Description: 根据ID查询ES
     * @Date: 2023年9月11日, 0011 下午 5:07:19
     * @param id
     * @return: java.util.Optional<T>
     */
    Optional<T> findById(ID id);

    /***
     * @Author: baiyx
     * @Description: 保存数据到ES
     * @Date: 2023年9月11日, 0011 下午 5:07:42
     * @Param:
     * @param entity
     * @return: S
     */
    <S extends T> S save(S entity);
}