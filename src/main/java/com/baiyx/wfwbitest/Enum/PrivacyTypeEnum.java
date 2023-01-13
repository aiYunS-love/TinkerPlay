package com.baiyx.wfwbitest.Enum;

import lombok.Getter;

/**
 * @Author: 白宇鑫
 * @Date: 2023-1-12 10:18
 * @Description: 隐私数据类型枚举
 */

@Getter
public enum PrivacyTypeEnum {

    /** 自定义（此项需设置脱敏的范围）*/
    CUSTOMER,

    /** 姓名 */
    NAME,

    /** 身份证号 */
    IDCARD,

    /** 手机号 */
    PHONE,

    /** 邮箱 */
    EMAIL,

}
