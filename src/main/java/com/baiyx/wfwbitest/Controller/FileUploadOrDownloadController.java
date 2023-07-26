package com.baiyx.wfwbitest.Controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baiyx.wfwbitest.Config.BucketPolicyConfigDto;
import com.baiyx.wfwbitest.Common.CommonResult;
import com.baiyx.wfwbitest.Common.MinioUploadDto;
import com.baiyx.wfwbitest.Config.MinioClientConfig;
import com.baiyx.wfwbitest.Entity.User;
import com.baiyx.wfwbitest.Entity.UserFile;
import com.baiyx.wfwbitest.InterviewQuestion.A;
import org.springframework.util.FileSystemUtils;
import com.baiyx.wfwbitest.Service.UserFileService;
import com.baiyx.wfwbitest.Utils.MinioUtil;
import io.minio.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.awt.Desktop;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Author: baiyx
 * @Date: 2022-9-30 下午 04:23
 * @Description: 文件上传下载
 */
@RestController
@Api(tags = "FileUploadOrDownloadController", description = "文件上传下载模块")
public class FileUploadOrDownloadController {

    @Autowired
    private UserFileService userFileService;

    @Value("${file-save-path}")
    private String fileSavePath;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadOrDownloadController.class);
    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    /**
     * 方法参数要加上@RequestParam("uploadFile"),与前端<input type="file" name="uploadFile" value="请选择文件">
     * 的name对应上
     * @param uploadFiles
     * @param req
     * @return
     */
    @ApiOperation("普通文件上传功能")
    @PostMapping("/upload")
    public HashSet upload(@RequestParam("uploadFile") MultipartFile[] uploadFiles, HttpServletRequest req) {
        // 结果集
        HashSet<String> resultset = new HashSet();
        List<UserFile> userFiles = new ArrayList<>();

        String filePath = "";
        for(MultipartFile uploadFile:uploadFiles){
            String format = sdf.format(new Date());
            File folder = new File(fileSavePath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = uploadFile.getOriginalFilename();
            if(oldName == null || "".equals(oldName.replace(" ","")) || "".equals(oldName.substring(0,oldName.lastIndexOf(".")).replace(" ",""))){
                resultset.add("未选择任何文件或有文件名为空!!!");
            }else{
                String newName = UUID.randomUUID().toString() +
                                 oldName.substring(oldName.lastIndexOf("."), oldName.length());
                try {
                    uploadFile.transferTo(new File(folder, newName));
                    filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                            req.getServerPort() + "/uploadFile/" + format + newName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                resultset.add(filePath);
                UserFile userFile = new UserFile();
                userFile.setFileName(newName)
                        .setExt(oldName.substring(oldName.lastIndexOf("."), oldName.length()))
                        .setPath(fileSavePath.contains(":")? folder + "\\" + newName : folder + "/" + newName)
                        .setSize(uploadFile.getSize())
                        .setType(uploadFile.getContentType())
                        // 用于测试,给ID为1的用户添加资料
                        .setUserId(1);
                userFiles.add(userFile);
            }
        }
        // 将文件信息存入数据库
        if(userFiles != null && userFiles.size() > 0){
            userFileService.save(userFiles);
        }
        return resultset;
    }

    /***
     * @Author: baiyx
     * @Description: @RequestPart用于将multipart/form-data类型数据映射到控制器处理方法的参数中
     * @Date: 2023年2月16日, 0016 下午 2:49:25
     * @param files
     * @return: com.baiyx.wfwbitest.common.CommonResult
     */
    @ApiOperation("文件上传Minio")
    @RequestMapping(value = "/upload2", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult upload2(@RequestPart("uploadFile2") MultipartFile[] files) {
        try {
            List<UserFile> userFiles = new ArrayList<>();
            //创建一个MinIO的Java客户端
            MinioClient minioClient =MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (isExist) {
                LOGGER.info("存储桶已经存在！");
            } else {
                // 创建存储桶并设置只读权限
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                BucketPolicyConfigDto bucketPolicyConfigDto = createBucketPolicyConfigDto(BUCKET_NAME);
                SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                        .bucket(BUCKET_NAME)
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minioClient.setBucketPolicy(setBucketPolicyArgs);
            }
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            List names = new ArrayList();
            List urls = new ArrayList();
            for (MultipartFile file : files){
                String filename = file.getOriginalFilename();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                // 设置存储对象名称
                String objectName = sdf.format(new Date()) + "/" + filename;
                // 使用putObject上传一个文件到存储桶中
                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .contentType(file.getContentType())
                        .stream(file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE).build();
                minioClient.putObject(putObjectArgs);
                LOGGER.info("文件上传成功!");
                names.add(filename);
                urls.add(ENDPOINT + "/" + BUCKET_NAME + "/" + objectName);
                UserFile userFile = new UserFile();
                userFile.setFileName(objectName)
                        .setExt('.'+ FilenameUtils.getExtension(filename))
                        .setPath(ENDPOINT + "/" + BUCKET_NAME + "/" + objectName)
                        .setSize(file.getSize())
                        .setType(file.getContentType())
                        // 用于测试,给ID为1的用户添加资料
                        .setUserId(1);
                userFiles.add(userFile);
            }
            // 将文件信息存入数据库
            if(userFiles != null && userFiles.size() > 0){
                userFileService.save(userFiles);
            }
            minioUploadDto.setNames(names);
            minioUploadDto.setUrls(urls);
            return CommonResult.success(minioUploadDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("上传发生错误: {}！", e.getMessage());
        }
        return CommonResult.failed();
    }

    /**
     * 创建存储桶的访问策略，设置为只读权限
     */
    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucketName) {
        BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::"+bucketName+"/*.**").build();
        return BucketPolicyConfigDto.builder()
                .Version("2012-10-17")
                .Statement(CollUtil.toList(statement))
                .build();
    }

    @ApiOperation("文件删除")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteMinioFile(@RequestParam("objectName") String objectName) {
        String filePath = userFileService.queryByFileName(objectName).getPath();
        if(objectName.contains("/") && filePath.contains("://")){
            try {
                MinioClient minioClient = MinioClient.builder()
                        .endpoint(ENDPOINT)
                        .credentials(ACCESS_KEY,SECRET_KEY)
                        .build();
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
                userFileService.deleteFile(objectName);
                return CommonResult.success(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return CommonResult.failed();
        }else{
            FileSystemUtils.deleteRecursively(new File(filePath));
            userFileService.deleteFile(objectName);
            return CommonResult.success(null);
        }
    }

    // 获取文件列表
    @ApiOperation("获取文件列表")
    @PostMapping("queryAllFile")
    @ResponseBody
    public Map<String, Object> queryAllFile(HttpSession session, HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page") == null? "1":request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit") == null? "100":request.getParameter("limit"));
        User user = (User) (session.getAttribute("user") == null? new User(1):session.getAttribute("user"));
        List<UserFile> files = userFileService.queryByUserId(user.getId(), page, limit);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("count", userFileService.queryFileCounts(user.getId()));
        res.put("data", files);
        return res;
    }

    /***
     * @Author: baiyx
     * @Description: 文件下载
     * @Date: 2023年6月7日, 0007 上午 11:07:25
     * @Param:
     * @param id
     * @param response
     * @return: void
     */
    @ApiOperation("文件下载")
    @GetMapping("download/{id}")
    public void download(@PathVariable("id") Integer id, HttpServletResponse response){
        String openStyle = "attachment";
        try{
            getFile(openStyle, id, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * @Author: baiyx
     * @Description: 更新文件下载次数
     * @Date: 2023年6月7日, 0007 上午 11:07:40
     * @Param:
     * @param openStyle
     * @param id
     * @param response
     * @return: void
     */
    public String getFile(String openStyle, Integer id, HttpServletResponse response) throws Exception {
        UserFile userFile = userFileService.queryByUserFileId(id);
        // String realPath = ResourceUtils.getURL("classpath").getPath() + userFile.getPath();
        String realPath = userFile.getPath();
        if(!realPath.startsWith("http")){
            // 普通上传
            FileInputStream is = new FileInputStream(new File(realPath));
            // 附件下载
            response.setHeader("content-disposition", openStyle + ";filename=" + URLEncoder.encode(userFile.getFileName(), "UTF-8"));
            // 获取响应response输出流
            ServletOutputStream os = response.getOutputStream();
            // 文件拷贝
            IOUtils.copy(is, os);
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
            if (openStyle.equals("attachment")) {
                userFile.setDownloadCounts(userFile.getDownloadCounts() + 1);
                userFileService.update(userFile);
            }
            return "下载成功";
        }else{
            // 上传到Minio桶
            MinioClient minioClient = MinioClientConfig.getMinioClient();
            if (minioClient == null) {
                return "连接MinIO服务器失败";
            }
            String s = MinioUtil.downloadFile(BUCKET_NAME,userFile.getFileName(),openStyle,response) != null ? "下载成功" : "下载失败";
            if (openStyle.equals("attachment") && "下载成功".equals(s)) {
                userFile.setDownloadCounts(userFile.getDownloadCounts() + 1);
                userFileService.update(userFile);
            }
            return s;
        }
    }

    /***
     * @Author: baiyx
     * @Description: 文件预览
     * @Date: 2023年6月7日, 0007 上午 11:10:00
     * @Param:
     * @param id
     * @param response
     * @return: void
     */
    @ApiOperation("文件预览")
    @GetMapping("preview/{id}")
    public void preview(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
        String openStyle = "inline";
        getFile(openStyle,id,response);
    }
}
