package com.baiyx.wfwbitest.Entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 白宇鑫
 * @Date: 2023年6月1日, 0001 下午 5:26:58
 * @Description: 短信发送实体类
 */
@ApiModel("短信发送实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms {

    private String[] phoneNumbers;
    private Integer templateId;
    private String smsSign;
    private String[] params;

}
