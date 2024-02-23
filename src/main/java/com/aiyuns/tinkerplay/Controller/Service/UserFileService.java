package com.aiyuns.tinkerplay.Controller.Service;

import com.aiyuns.tinkerplay.Entity.UserFile;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Author: aiYunS
 * @Date: 2023年6月7日, 0007 上午 9:25:26
 * @Description: 文件下载Service层
 */
public interface UserFileService {

    // 根据用户id获得文件列表
    List<UserFile> queryByUserId(Integer id, Integer page, Integer limit);

    // 根据用户id获得文件数
    int queryFileCounts(Integer id);

    // 文件上传,保存到数据库
    @Async
    void save(List<UserFile> userFiles);

    // 下载文件
    UserFile queryByUserFileId(Integer id);

    // 更新文件下载次数
    @Async
    void update(UserFile userFile);

    // 删除文件
    @Async
    void deleteFile(String objectName);

    // 根据文件名查询
    UserFile queryByFileName(String objectName);
}
