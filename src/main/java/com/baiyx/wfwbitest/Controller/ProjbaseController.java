package com.baiyx.wfwbitest.Controller;

import com.baiyx.wfwbitest.Dao.ProjBaseDao;
import com.baiyx.wfwbitest.ServiceImpl.ProjBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @Author: 白宇鑫
 * @Date: 2022-12-5 16:30
 * @Description: 控制器
 */

@RestController
@RequestMapping(value ="ProjbaseController",produces = "application/json;charset=UTF-8")
public class ProjbaseController {

    @Autowired
    ProjBaseDao projBaseDao;

    // 不做任何处理,常规方式读取数据,处理数据,写入数据
    // @WebLog(description = "分析异常数据")
    @RequestMapping(value = "AnalyzingAbnormalData",method= RequestMethod.POST,produces = "application/json")
    public void AnalyzingAbnormalData(@RequestBody Map map) {
        new ProjBaseServiceImpl(projBaseDao).readProjbase(map);
    }

}
