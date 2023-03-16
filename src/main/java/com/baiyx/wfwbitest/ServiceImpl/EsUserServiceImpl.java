package com.baiyx.wfwbitest.ServiceImpl;

import com.baiyx.wfwbitest.Dao.UserDao;
import com.baiyx.wfwbitest.Entity.EsUser;
import com.baiyx.wfwbitest.Interface.EsUserRepository;
import com.baiyx.wfwbitest.Service.EsUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2023年3月16日, 0016 下午 1:22:32
 * @Description: 人员搜索管理Service实现类
 */
@Service
public class EsUserServiceImpl implements EsUserService {

    @Autowired
    private UserDao UserDao;

    @Autowired
    private EsUserRepository esUserRepository;

    @Override
    public int importAll() {
        List<EsUser> esProductList = UserDao.findAll3();
        Iterable<EsUser> esProductIterable = esUserRepository.saveAll(esProductList);
        Iterator<EsUser> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        esUserRepository.deleteById(id);
    }

    @Override
    public EsUser create(Long id) {
        EsUser result = null;
        List<EsUser> esUserList = UserDao.findById2(id);
        if (esUserList.size() > 0) {
            EsUser esProduct = esUserList.get(0);
            result = (EsUser) esUserRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsUser> esProductList = new ArrayList<>();
            for (Long id : ids) {
                EsUser esProduct = new EsUser();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            esUserRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<EsUser> search(String sex, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esUserRepository.findBySex(sex, pageable);
    }
}
