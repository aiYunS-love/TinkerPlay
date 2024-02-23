package com.aiyuns.tinkerplay.Entity;

/**
 * @Author: aiYunS
 * @Date: 2021/6/30 下午 02:25
 * @Description: 行政区实体类
 */
public class xzq {

    int bsm;
    String sss;
    String ssx;
    String bzxzqbm;
    String bdcxzqbm;
    String createby;
    String createtime;
    String editby;
    String edittime;
    String deptcode;

    public int getBsm() {
        return bsm;
    }

    public void setBsm(int bsm) {
        this.bsm = bsm;
    }

    public String getSss() {
        return sss;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public String getSsx() {
        return ssx;
    }

    public void setSsx(String ssx) {
        this.ssx = ssx;
    }

    public String getBzxzqbm() {
        return bzxzqbm;
    }

    public void setBzxzqbm(String bzxzqbm) {
        this.bzxzqbm = bzxzqbm;
    }

    public String getBdcxzqbm() {
        return bdcxzqbm;
    }

    public void setBdcxzqbm(String bdcxzqbm) {
        this.bdcxzqbm = bdcxzqbm;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEditby() {
        return editby;
    }

    public void setEditby(String editby) {
        this.editby = editby;
    }

    public String getEdittime() {
        return edittime;
    }

    public void setEdittime(String edittime) {
        this.edittime = edittime;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    @Override
    public String toString() {
        return "xzq{" +
                "bsm=" + bsm +
                ", sss='" + sss + '\'' +
                ", ssx='" + ssx + '\'' +
                ", bzxzqbm='" + bzxzqbm + '\'' +
                ", bdcxzqbm='" + bdcxzqbm + '\'' +
                ", createby='" + createby + '\'' +
                ", createtime='" + createtime + '\'' +
                ", editby='" + editby + '\'' +
                ", edittime='" + edittime + '\'' +
                ", deptcode='" + deptcode + '\'' +
                '}';
    }
}
