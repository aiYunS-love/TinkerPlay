package com.baiyx.wfwbitest.Interface;

import com.baiyx.wfwbitest.Entity.EsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: baiyx
 * @Date: 2023年3月16日, 0016 下午 1:07:55
 * @Description: 人员ES操作类
 */
public interface EsUserRepository extends ElasticsearchRepository<EsUser, Long> {

    /**
     * 根据性别 搜索查询
     */
    Page<EsUser> findBySex( String sex, Pageable page);

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
