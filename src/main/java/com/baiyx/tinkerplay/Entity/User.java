package com.baiyx.tinkerplay.Entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baiyx.tinkerplay.CustomAnnotations.PrivacyEncrypt;
import com.baiyx.tinkerplay.Enum.PrivacyTypeEnum;
import com.baiyx.tinkerplay.Config.Properties.DateConverterProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: baiyx
 * @Date: 2021/6/30 上午 10:23
 * @Description: user实体类
 */

@Schema(name = "用户实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 4359709211352400087L;

    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("姓名")
    @PrivacyEncrypt(type = PrivacyTypeEnum.CUSTOMER, prefixNoMaskLen = 1, suffixNoMaskLen = 1, symbol = "$")
    private String username;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("性别")
    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@ExcelIgnore  //解决Date转换出现的NoSuchMethodError异常 过略该类型的属性不导出
    @ExcelProperty(value = "出生年月日", converter = DateConverterProperties.class)
    private Date birthday;

    @ExcelProperty("身份证")
    @PrivacyEncrypt(type = PrivacyTypeEnum.ID_CARD)
    private String ID_CARD;

    @ExcelProperty("电话")
    @PrivacyEncrypt(type = PrivacyTypeEnum.PHONE)
    private String PHONE;

    @ExcelProperty("邮箱")
    @PrivacyEncrypt(type = PrivacyTypeEnum.EMAIL)
    private String EMAIL;

    // 测试用
    public User(String username){
        this.username = username;
    }
    // 测试用
    public User(Integer id){
        this.id = id;
    }

    public User(Integer id, String username, String address, String sex, Date birthday) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.sex = sex;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @JsonProperty("ID_CARD")
    public String getID_CARD() {
        return ID_CARD;
    }

    public void setID_CARD(String ID_CARD) {
        this.ID_CARD = ID_CARD;
    }

    @JsonProperty("PHONE")
    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    @JsonProperty("EMAIL")
    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", IDCARD='" + ID_CARD + '\'' +
                ", PHONE='" + PHONE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
