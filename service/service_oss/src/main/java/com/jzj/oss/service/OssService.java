package com.jzj.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/7/27 17:57
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
