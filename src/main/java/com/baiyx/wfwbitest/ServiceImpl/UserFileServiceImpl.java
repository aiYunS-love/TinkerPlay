package com.baiyx.wfwbitest.ServiceImpl;

import com.baiyx.wfwbitest.Dao.UserFileDao;
import com.baiyx.wfwbitest.Entity.UserFile;
import com.baiyx.wfwbitest.Service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2023年6月7日, 0007 上午 9:27:18
 * @Description: 文件下载Service层实现层
 */
@Service
public class UserFileServiceImpl implements UserFileService{

    @Autowired
    private UserFileDao userFileDao;

    /**
     * 根据用户id获得文件列表
     * @param id
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<UserFile> queryByUserId(Integer id, Integer page, Integer limit){
        // page表示第几页，limit表示每页显示多少行数据
        int begin = (page-1)*limit;   // 该计算方法获得开始的位置
        int offset = limit;
        return userFileDao.queryByUserId(id, begin, limit);
    }

    /**
     * 根据用户id获得文件数
     * @param id
     * @return
     */
    @Override
    public int queryFileCounts(Integer id){
        return userFileDao.queryFileCount(id);
    }

    // 文件上传,信息保存到数据库
    @Override
    public void save(List<UserFile> userFiles) {
        for(UserFile userfile : userFiles){
            userfile.setDownloadCounts(0).setUploadTime(new Date());
        }
        userFileDao.save(userFiles);
    }

    // 下载文件
    @Override
    public UserFile queryByUserFileId(Integer id) {
        return userFileDao.queryByUserFileId(id);
    }

    // 更新文件下载次数
    @Override
    public void update(UserFile userFile) {
        userFileDao.update(userFile);
    }

    // 删除文件
    @Override
    public void deleteFile(String objectName) {
        userFileDao.deleteFile(objectName);
    }

    // 根据文件名查询
    @Override
    public UserFile queryByFileName(String objectName){
        return userFileDao.queryByFileName(objectName);
    }
}
