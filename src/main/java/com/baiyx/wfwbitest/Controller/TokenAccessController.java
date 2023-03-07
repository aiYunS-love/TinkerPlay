package com.baiyx.wfwbitest.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baiyx.wfwbitest.CustomAnnotations.WebLog;
import com.baiyx.wfwbitest.Entity.TokenAccess;
import com.baiyx.wfwbitest.Service.TokenAccessService;
import com.baiyx.wfwbitest.Utils.ResolveTokenUtil;
import com.baiyx.wfwbitest.Utils.TokenCreateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author: 白宇鑫
 * @Date: 2022-10-12 下午 04:09
 * @Description: 测试通过解析Token,查询数据库数据的权限状态,并返回状态
 */

@RestController
@RequestMapping(value ="AccessControl",produces = "application/json;charset=UTF-8")
@Api(tags = "TokenAccessController", description = "解析Token校验权限模块")
public class TokenAccessController {

    @Autowired
    TokenAccessService tokenAccessService;

    @ApiOperation(value = "解析Token校验权限")
    @WebLog(description = "权限检查")
    @RequestMapping(value = "check",method= {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @CrossOrigin
    public JSONObject check(HttpServletRequest request){
        JSONObject data = new JSONObject();
        TokenAccess tokenAccess =  ResolveTokenUtil.resolveToken(request.getHeader("x-gisq-token"));
        int status = tokenAccessService.checkStatus(tokenAccess);
        data.put("status",status);
        return data;
    }

    @ApiOperation(value = "获取登录Token")
    @WebLog(description = "获取登录Token")
    @RequestMapping(value = "/jwt/sign",method= RequestMethod.POST)
    @CrossOrigin
    public String sign(@RequestBody String request){
        JSONObject jsonObject = JSON.parseObject(request);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        return TokenCreateUtil.token(username,password);
    }


}
