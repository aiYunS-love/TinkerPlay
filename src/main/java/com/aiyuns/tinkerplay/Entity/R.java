package com.aiyuns.tinkerplay.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: aiYunS
 * @Date: 2022-9-25 上午 11:05
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    private Integer code;
    private String msg;
    private Object data;

    public static R ok(String msg){
        return new R(200,msg,null);
    }

    public static R ok(String msg,Object data){
        return new R(200,msg,data);
    }

    public static R error(String msg){
        return new R(500,msg,null);
    }

    public static R error(String msg,Object data){
        return new R(500,msg,data);
    }

}
