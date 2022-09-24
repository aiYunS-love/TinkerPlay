package com.baiyx.wfwbitest.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-5 上午 09:27
 * @Description: projbase表结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projbase {

    @ExcelProperty("ywh")
    private String ywh;
    @ExcelProperty("projId")
    private String projId;
    @ExcelProperty("jsonObj")
    private String jsonObj;
    @ExcelProperty("cjsj")
    private Date cjsj;
    @ExcelProperty("matterCode")
    private String matterCode;
    @ExcelProperty("projectName")
    private String projectName;
    @ExcelProperty("gmtApply")
    private String gmtApply;
    @ExcelProperty("applyName")
    private String applyName;
    @ExcelProperty("applyCardNo")
    private String applyCardNo;
    @ExcelProperty("bdcqzh")
    private String bdcqzh;
    @ExcelProperty("bdcdjzmh")
    private String bdcdjzmh;
    @ExcelProperty("zl")
    private String zl;
    @ExcelProperty("areaCode")
    private String areaCode;
    @ExcelProperty("zt")
    private Integer zt;

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getJsonObj() {
        return jsonObj;
    }

    public void setJsonObj(String jsonObj) {
        this.jsonObj = jsonObj;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getMatterCode() {
        return matterCode;
    }

    public void setMatterCode(String matterCode) {
        this.matterCode = matterCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGmtApply() {
        return gmtApply;
    }

    public void setGmtApply(String gmtApply) {
        this.gmtApply = gmtApply;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplyCardNo() {
        return applyCardNo;
    }

    public void setApplyCardNo(String applyCardNo) {
        this.applyCardNo = applyCardNo;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    @Override
    public String toString() {
        return "projbase{" +
                "ywh='" + ywh + '\'' +
                ", projId='" + projId + '\'' +
                ", jsonObj='" + jsonObj + '\'' +
                ", cjsj=" + cjsj +
                ", matterCode='" + matterCode + '\'' +
                ", projectName='" + projectName + '\'' +
                ", gmtApply='" + gmtApply + '\'' +
                ", applyName='" + applyName + '\'' +
                ", applyCardNo='" + applyCardNo + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", bdcdjzmh='" + bdcdjzmh + '\'' +
                ", zl='" + zl + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", zt=" + zt +
                '}';
    }
}
