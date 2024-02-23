package com.aiyuns.tinkerplay.Common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 文件上传返回结果
 */
@Data
@EqualsAndHashCode
public class MinioUploadDto {
    @Schema(name = "文件访问URL")
    private List<String> urls;
    @Schema(name = "文件名称")
    private List<String> names;
}
