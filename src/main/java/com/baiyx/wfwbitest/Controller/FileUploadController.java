package com.baiyx.wfwbitest.Controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baiyx.wfwbitest.Config.BucketPolicyConfigDto;
import com.baiyx.wfwbitest.common.CommonResult;
import com.baiyx.wfwbitest.common.MinioUploadDto;
import io.minio.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-30 下午 04:23
 * @Description: 多文件上传
 */
@RestController
public class FileUploadController {

    @Value("${file-save-path}")
    private String fileSavePath;
    HashSet<String> resultset = new HashSet();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
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
    @PostMapping("/upload")
    public HashSet upload(@RequestParam("uploadFile") MultipartFile[] uploadFiles, HttpServletRequest req) {
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
            }
        }
        return resultset;
    }

    /***
     * @Author: 白宇鑫
     * @Description: @RequestPart用于将multipart/form-data类型数据映射到控制器处理方法的参数中
     * @Date: 2023年2月16日, 0016 下午 2:49:25
     * @param files
     * @return: com.baiyx.wfwbitest.common.CommonResult
     */
    @ApiOperation("文件上传")
    @RequestMapping(value = "/upload2", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult upload2(@RequestPart("uploadFile2") MultipartFile[] files) {
        try {
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
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("objectName") String objectName) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
            return CommonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed();
    }

}
