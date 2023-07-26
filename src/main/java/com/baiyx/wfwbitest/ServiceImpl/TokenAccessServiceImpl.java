package com.baiyx.wfwbitest.ServiceImpl;

import com.baiyx.wfwbitest.Dao.TokenAccessDao;
import com.baiyx.wfwbitest.Entity.TokenAccess;
import com.baiyx.wfwbitest.Service.TokenAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: baiyx
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
