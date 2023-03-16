package com.baiyx.wfwbitest.Interface;

import com.baiyx.wfwbitest.Entity.EsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: 白宇鑫
 * @Date: 2023年3月16日, 0016 下午 1:07:55
 * @Description: 人员ES操作类
 */
public interface EsUserRepository extends ElasticsearchRepository<EsUser, Long> {

    /**
     * 搜索查询
     */
    Page<EsUser> findBySex( String sex, Pageable page);
}
