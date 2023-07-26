package com.baiyx.wfwbitest.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: baiyx
 * @Date: 2022-10-12 下午 05:15
 * @Description: 实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenAccess {

    private String staff_name;
    private String user_id;
    private String username;
    private Integer status;

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
