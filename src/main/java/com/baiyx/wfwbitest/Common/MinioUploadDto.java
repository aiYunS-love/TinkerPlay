package com.baiyx.wfwbitest.Common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 文件上传返回结果
 * Created by macro on 2019/12/25.
 */
@Data
@EqualsAndHashCode
public class MinioUploadDto {
    @ApiModelProperty("文件访问URL")
    private List<String> urls;
    @ApiModelProperty("文件名称")
    private List<String> names;
}
