package com.baiyx.wfwbitest.Service;

import com.baiyx.wfwbitest.Entity.EsUser;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2023年3月16日, 0016 下午 1:17:57
 * @Description: 人员搜索管理Service
 */
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
     * 根据关键字搜索名称或者副标题
     */
    Page<EsUser> search(String sex, Integer pageNum, Integer pageSize);
}
