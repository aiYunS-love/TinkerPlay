package com.baiyx.wfwbitest.Controller.Elasticsearch;

import com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

/**
 * @Author: baiyx
 * @Date: 2023年9月13日, 0013 上午 11:19:58
 * @Description: EsUserService
 */
public interface EsUserService {

    long countByname(String name);

    long deleteByname(String name);

    // 批量保存到es
    int saveAllUsers();

    // 按照姓名查找es
    List findByName(String lastName);

    EsUser searchById(Long id);

    Long saveEsUser(EsUser esUser);

    SearchHits<EsUser> searchAll();

}
