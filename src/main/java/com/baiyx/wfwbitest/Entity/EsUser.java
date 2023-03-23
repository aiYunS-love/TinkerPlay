package com.baiyx.wfwbitest.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 白宇鑫
 * @Date: 2023年3月16日, 0016 下午 12:52:12
 * @Description: 搜索中的商品信息
 */
@Document(indexName = "user", shards = 1,replicas= 0)
public class EsUser implements Serializable {

    @Id
    private Long id;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String name;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String address;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @Field(analyzer = "ik_max_word", type = FieldType.Date)
    private Date birthday;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String ID_CARD;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String PHONE;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String EMAIL;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String keywords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
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

    public String getID_CARD() {
        return ID_CARD;
    }

    public void setID_CARD(String ID_CARD) {
        this.ID_CARD = ID_CARD;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "EsUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", ID_CARD='" + ID_CARD + '\'' +
                ", PHONE='" + PHONE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
