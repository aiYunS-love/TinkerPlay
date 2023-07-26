package com.baiyx.wfwbitest.Entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: baiyx
 * @Date: 2023年6月7日, 0007 上午 9:08:11
 * @Description: 文件下载实体类
 */
@ApiModel("用户文件实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true) //为生成的设置方法返回当前对象，以支持链式调用。
public class UserFile {
    private Integer id;
    private String fileName;
    private String ext;
    private String path;
    private long size;
    private String type;
    private Integer downloadCounts;
    private Date uploadTime;
    private Integer userId;
}
