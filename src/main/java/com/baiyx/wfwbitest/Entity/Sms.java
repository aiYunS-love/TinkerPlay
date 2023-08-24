package com.baiyx.wfwbitest.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: baiyx
 * @Date: 2023年6月1日, 0001 下午 5:26:58
 * @Description: 短信发送实体类
 */
@Schema(name = "短信发送实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms {

    private String[] phoneNumbers;
    private Integer templateId;
    private String smsSign;
    private String[] params;

}
