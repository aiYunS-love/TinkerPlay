package com.baiyx.wfwbitest.serviceImpl;

import com.baiyx.wfwbitest.dao.TokenAccessDao;
import com.baiyx.wfwbitest.entity.TokenAccess;
import com.baiyx.wfwbitest.service.TokenAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: 白宇鑫
 * @Date: 2022-10-12 下午 04:50
 * @Description: 说明
 */
@Service
public class TokenAccessServiceImpl implements TokenAccessService {

    @Autowired
    TokenAccessDao tokenAccessDao;

    @Override
    public int checkStatus(TokenAccess tokenAccess) {
        int status = 0;
        if(tokenAccessDao.checkStatus(tokenAccess) != null){
            status = tokenAccessDao.checkStatus(tokenAccess);
        }
        return status;
    }
}
