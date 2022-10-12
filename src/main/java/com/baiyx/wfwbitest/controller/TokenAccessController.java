package com.baiyx.wfwbitest.controller;

import com.alibaba.fastjson.JSONObject;
import com.baiyx.wfwbitest.customAnnotations.WebLog;
import com.baiyx.wfwbitest.entity.TokenAccess;
import com.baiyx.wfwbitest.service.TokenAccessService;
import com.baiyx.wfwbitest.util.ResolveTokenUtil;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: 白宇鑫
 * @Date: 2022-10-12 下午 04:09
 * @Description: 测试通过解析Token,查询数据库数据的权限状态,并返回状态
 */

@RestController
@RequestMapping(value ="AccessControl",produces = "application/json;charset=UTF-8")
public class TokenAccessController {

    @Autowired
    TokenAccessService tokenAccessService;

    @WebLog(description = "查询所有")
    @RequestMapping(value = "check",method= RequestMethod.POST,produces = "application/json")
    public int check(@RequestBody String token){
        JSONObject jsonObject = JSONObject.parseObject(token);
        TokenAccess tokenAccess =  ResolveTokenUtil.resolveToken(jsonObject.getString("token"));
        return tokenAccessService.checkStatus(tokenAccess);
    }
}
