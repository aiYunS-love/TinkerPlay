package com.aiyuns.tinkerplay.Controller.Service.ServiceImpl;

import com.aiyuns.tinkerplay.Controller.Service.ServiceImpl.Dao.TokenAccessDao;
import com.aiyuns.tinkerplay.Entity.TokenAccess;
import com.aiyuns.tinkerplay.Controller.Service.TokenAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: aiYunS
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
