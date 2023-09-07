package com.baiyx.wfwbitest.Controller.Service;

import com.baiyx.wfwbitest.Entity.EsUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: baiyx
 * @Date: 2023年3月16日, 0016 下午 1:17:57
 * @Description: 人员搜索管理Service
 */
@Mapper
public interface EsUserService {

    /**
     * 从数据库中导入所有人员信息到ES
     */
    int importAll();

    /**
     * 根据id删除人员
     */
    void delete(Long id);

    /**
     * 根据id创建人员
     */
    EsUser create(Long id);

    /**
     * 批量删除人员
     */
    void delete(List<Long> ids);

    /**
     * 根据性别搜索
     */
    Page<EsUser> searchBySex(String sex, Integer pageNum, Integer pageSize);

    /**
     * 根据姓名或关键字搜索
     */
    Page<EsUser> searchByNameOrKeywords(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据姓名或证件号或关键字搜索
     */
    Page<EsUser> searchByNameOrCARDOrKeywords(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据关键字搜索
     */
    Page<EsUser> searchByKeyword(String keywords, Integer pageNum, Integer pageSize);
}
