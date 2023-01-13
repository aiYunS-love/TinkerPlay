package com.baiyx.wfwbitest.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2021/9/8 下午 08:20
 * @Description: 包装查询请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequestVo {

    //对象
    private User user;

    //登记类型
    private String djlx;

    //办理时长
    private int blsc = 32;

    //权利类型
    private String qllx;

    //业务号
    private String ywh;

    //用户id
    //private int id;

    //用户名字
    //private String name;

    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    //时间区间
    private String qj;

    //超时
    private String timeOut;

    //行政区编码
    private String xzqbm;

    //行政区级别
    private String xzqjib;

    //
    private List<String> jkzl;

    //是否查询列表
    private boolean lists;

    //查询页
    private int start;

    //每页大小
    private int size;

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public int getBlsc() {
        return blsc;
    }

    public void setBlsc(int blsc) {
        this.blsc = blsc;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public Date getKssj() {
        return startTime;
    }

    public void setKssj(Date kssj) {
        if(kssj == null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.startTime = dateFormat.parse(dateFormat.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            this.startTime = kssj;
        }
    }

    public Date getJssj() {
        return endTime;
    }

    public void setJssj(Date jssj) {
        if (jssj == null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.endTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            this.endTime = jssj;
        }
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getXzqbm() {
        return xzqbm;
    }

    public void setXzqbm(String xzqbm) {
        this.xzqbm = xzqbm;
    }

    public String getXzqjib() {
        return xzqjib;
    }

    public void setXzqjib(String xzqjib) {
        this.xzqjib = xzqjib;
    }

    public List<String> getJkzl() {
        return jkzl;
    }

    public void setJkzl(List<String> jkzl) {
        this.jkzl = jkzl;
    }

    public boolean isLists() {
        return lists;
    }

    public void setLists(boolean lists) {
        this.lists = lists;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
