package com.jzj.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.jzj.oss.service.OssService;
import com.jzj.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/7/27 17:57
 */
@Service
public class OssServiceImpl implements OssService {


    @Override
    public String uploadFileAvatar(InputStream inputStream, String module, String fileName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ConstantPropertiesUtils.END_POINT, ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);

        //判断BUCKET_NAME是否存在
        if(!ossClient.doesBucketExist(ConstantPropertiesUtils.BUCKET_NAME)){
            ossClient.createBucket(ConstantPropertiesUtils.BUCKET_NAME);
            ossClient.setBucketAcl(ConstantPropertiesUtils.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }
        //上传文件
        //构建日期路径
        String timeFolder = new DateTime().toString("/yyyy/MM/dd/");
        //文件名生成
        fileName=UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
        String key=module+timeFolder+fileName;
        ossClient.putObject(ConstantPropertiesUtils.BUCKET_NAME, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //文件的URL地址
        return "https://" + ConstantPropertiesUtils.BUCKET_NAME + "." + ConstantPropertiesUtils.END_POINT + "/" + key;
    }

    @Override
    public void removeFile(String url) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ConstantPropertiesUtils.END_POINT, ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);

        //文件名（服务器上的文件路径）
        String host = "https://" + ConstantPropertiesUtils.BUCKET_NAME + "." + ConstantPropertiesUtils.END_POINT+ "/";
        String objectName = url.substring(host.length());
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(ConstantPropertiesUtils.BUCKET_NAME, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }


}
