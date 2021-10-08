package com.jzj.oss.service;

import java.io.InputStream;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/7/27 17:57
 */
public interface OssService {
   

    void removeFile(String url);

    String uploadFileAvatar(InputStream inputStream, String module, String originalFilename);
}
