package com.baiyx.wfwbitest.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-30 下午 04:23
 * @Description: 多文件上传
 */
@RestController
public class FileUploadController {

    @Value("${file-save-path}")
    private String fileSavePath;
    ArrayList<String> mylist=new ArrayList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    /**
     * 方法参数要加上@RequestParam("uploadFile"),与前端<input type="file" name="uploadFile" value="请选择文件">
     * 的name对应上
     * @param uploadFiles
     * @param req
     * @return
     */
    @PostMapping("/upload")
    public ArrayList upload(@RequestParam("uploadFile") MultipartFile[] uploadFiles, HttpServletRequest req) {
        String filePath = "";
        for(MultipartFile uploadFile:uploadFiles){
            String format = sdf.format(new Date());
            File folder = new File(fileSavePath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
                String oldName = uploadFile.getOriginalFilename();
                String newName = UUID.randomUUID().toString() +
                        oldName.substring(oldName.lastIndexOf("."), oldName.length());
                try {
                    uploadFile.transferTo(new File(folder, newName));
                    filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                            req.getServerPort() + "/uploadFile/" + format + newName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() +
                    oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                uploadFile.transferTo(new File(folder, newName));
                filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                        req.getServerPort() + "/uploadFile/" + format + newName;
            } catch (IOException e) {
                e.printStackTrace();
            }

            mylist.add(filePath);
        }
        return mylist;
    }

}
