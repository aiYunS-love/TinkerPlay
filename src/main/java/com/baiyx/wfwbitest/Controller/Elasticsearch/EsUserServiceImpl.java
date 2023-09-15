package com.baiyx.wfwbitest.Controller.Elasticsearch;

import com.baiyx.wfwbitest.Controller.Elasticsearch.EsEntity.EsUser;
import com.baiyx.wfwbitest.Controller.Elasticsearch.Repository.EsUserRepository;
import com.baiyx.wfwbitest.Controller.Service.ServiceImpl.Dao.UserDao;
import jakarta.annotation.Resource;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static io.lettuce.core.GeoArgs.Unit.m;

/**
 * @Author: baiyx
 * @Date: 2023年9月13日, 0013 下午 2:21:15
 * @Description: es的service实现层
 */
@Service
public class EsUserServiceImpl implements EsUserService{

    private final EsUserRepository esUserRepository;

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    @Resource
    private UserDao userDao;

    @Autowired
    public EsUserServiceImpl(EsUserRepository esUserRepository, ElasticsearchRestTemplate elasticsearchTemplate) {
        this.esUserRepository = esUserRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public long countByname(String name) {
        return esUserRepository.countByname(name);
    }

    public long deleteByname(String name){
        return esUserRepository.deleteByname(name);
    }

    @Override
    public int saveAllUsers() {
        int result = 0;
        List<EsUser> esUserList = userDao.findAll3();
        Iterable<EsUser> esUserIterable = elasticsearchTemplate.save(esUserList);
        Iterator<EsUser> iterator = esUserIterable.iterator();
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public List findByName(String name) {
        return esUserRepository.findByname(name);
    }

    @Override
    public EsUser searchById(Long id) {
        EsUser esUser = esUserRepository.searchById(id.toString());
        return esUser;
    }

    @Override
    public Long saveEsUser(EsUser esUser) {
        return esUserRepository.save(esUser).getId();
    }

    public SearchHits<EsUser> searchAll(){
        MatchAllQueryBuilder matchAllQueryBuilder = new MatchAllQueryBuilder();
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(matchAllQueryBuilder).build();
        return elasticsearchTemplate.search(query, EsUser.class);
    }
}
