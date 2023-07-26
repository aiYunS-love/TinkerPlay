package com.baiyx.wfwbitest.Entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: baiyx
 * @Date: 2022-9-5 上午 09:27
 * @Description: projbaseException表结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjbaseException {

    @ExcelProperty("projId")
    private String projId;
    @ExcelProperty("cjsj")
    private String cjsj;
    @ExcelProperty("projectName")
    private String projectName;
    @ExcelProperty("applyName")
    private String applyName;
    @ExcelProperty("applyCardNo")
    private String applyCardNo;
    @ExcelProperty("qxdm")
    private String qxdm;
    @ExcelProperty("recvUserName")
    private String recvUserName;
    @ExcelProperty("recvDeptCode")
    private String recvDeptCode;
    @ExcelProperty("recvUserId")
    private String recvUserId;
    @ExcelProperty("faceValidationResult")
    private String faceValidationResult;

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getRecvUserName() {
        return recvUserName;
    }

    public void setRecvUserName(String recvUserName) {
        this.recvUserName = recvUserName;
    }

    public String getRecvDeptCode() {
        return recvDeptCode;
    }

    public void setRecvDeptCode(String recvDeptCode) {
        this.recvDeptCode = recvDeptCode;
    }

    public String getRecvUserId() {
        return recvUserId;
    }

    public void setRecvUserId(String recvUserId) {
        this.recvUserId = recvUserId;
    }

    public String getFaceValidationResult() {
        return faceValidationResult;
    }

    public void setFaceValidationResult(String faceValidationResult) {
        this.faceValidationResult = faceValidationResult;
    }

    @Override
    public String toString() {
        return "ProjbaseException{" +
                "projId='" + projId + '\'' +
                ", cjsj=" + cjsj +
                ", projectName='" + projectName + '\'' +
                ", applyName='" + applyName + '\'' +
                ", applyCardNo='" + applyCardNo + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", recvUserName='" + recvUserName + '\'' +
                ", recvDeptCode='" + recvDeptCode + '\'' +
                ", recvUserId='" + recvUserId + '\'' +
                ", faceValidationResult='" + faceValidationResult + '\'' +
                '}';
    }
}
