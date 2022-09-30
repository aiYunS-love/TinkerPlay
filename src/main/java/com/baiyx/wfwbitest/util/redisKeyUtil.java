package com.baiyx.wfwbitest.util;

import com.baiyx.wfwbitest.entity.QueryRequestVo;
import com.baiyx.wfwbitest.entity.User;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-28 上午 11:23
 * @Description: 通过方法名和参数类型,动态拼接redis的key返回
 */

public class redisKeyUtil {

    public static String redisKey(String methodName, User user, Object[] args){
        StringBuffer redisKey = new StringBuffer();
        if(methodName.contains("find")){
            if(methodName.contains("Id")){
                redisKey.append("*");
                redisKey.append(methodName);
                redisKey.append(":");
                redisKey.append(user.getId());
                redisKey.append("*");
            }else if(methodName.contains("Name")){
                redisKey.append("*");
                redisKey.append(methodName);
                redisKey.append(":");
                redisKey.append(user.getUsername());
                redisKey.append("*");
            }else if(methodName.contains("Time")){
                redisKey.append("*");
                redisKey.append(methodName);
                redisKey.append(":");
                for(int i=0;i<args.length;i++){
                    if(i == args.length-1){
                        redisKey.append(args[i]);
                        redisKey.append("*");
                    }else{
                        redisKey.append(args[i]);
                        redisKey.append("_");
                    }
                }
            }
        }
        return redisKey.toString();
    }

    public static String redisKey(String methodName, QueryRequestVo queryRequestVo, Object[] args){
        StringBuffer redisKey = new StringBuffer();
        if(methodName.contains("find")){
            if(methodName.contains("Id")){
                redisKey.append("*");
                redisKey.append(methodName);
                redisKey.append(":");
                redisKey.append(queryRequestVo.getUser().getId());
                redisKey.append("*");
            }else if(methodName.contains("Name")){
                redisKey.append("*");
                redisKey.append(methodName);
                redisKey.append(":");
                redisKey.append(queryRequestVo.getUser().getUsername());
                redisKey.append("*");
            }else if(methodName.contains("Time")){
                redisKey.append("*");
                redisKey.append(methodName);
                redisKey.append(":");
                for(int i=0;i<args.length;i++){
                    if(i == args.length-1){
                        redisKey.append(args[i]);
                        redisKey.append("*");
                    }else{
                        redisKey.append(args[i]);
                        redisKey.append("_");
                    }
                }
            }
        }
        return redisKey.toString();
    }
}
