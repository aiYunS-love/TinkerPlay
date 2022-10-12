package com.baiyx.wfwbitest.entity;

import com.baiyx.wfwbitest.customAnnotations.excelRescoure;

/**
 * @Author: 白宇鑫
 * @Date: 2022-10-12 上午 09:56
 * @Description: ExcelPOJO实体类
 *  * 实体类所有成员变量都需要有GET,SET方法
 *  * 所有成员变量都要加上注解@excelRescoure(value = "?"),?为Excel真实列名，必须一一对应
 *  * @excelRescoure(value = "?"),?可为空,需要用到才赋值
 *  * 成员变量目前只允许String,Double,Interge,Float
 */

public class ExcelPOJO {

    public ExcelPOJO() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPasswork() {
        return passwork;
    }
    public void setPasswork(String passwork) {
        this.passwork = passwork;
    }
    public String getLook() {
        return look;
    }
    public void setLook(String look) {
        this.look = look;
    }

    @excelRescoure(value = "XM")
    private  String name;
    @excelRescoure(value = "SFZH")
    private  String passwork;
    @excelRescoure()
    private  String look;

    @Override
    public String toString(){
        return "name:"+this.getName()+",passwork:"+this.getPasswork()+",look:"+this.getLook();
    }
}
