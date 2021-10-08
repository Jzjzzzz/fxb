package com.jzj.oss.controller;

import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.R;
import com.jzj.commonutils.ResponseEnum;
import com.jzj.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/7/27 17:57
 */
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Resource
    private OssService ossService;

    @ApiOperation("上传OSS文件")
    @PostMapping("/upload/{module}")
    public R uploadOssFile(@ApiParam(value = "文件", required = true) MultipartFile  file,@ApiParam(value = "模块", required = true) @PathVariable String module){
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadUrl = ossService.uploadFileAvatar(inputStream, module, originalFilename);
            //返回r对象
            return R.ok().message("文件上传成功").data("url", uploadUrl);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("删除OSS文件")
    @DeleteMapping("/remove")
    public R remove(@ApiParam(value = "要删除的文件路径", required = true) @RequestParam("url") String url) {
        ossService.removeFile(url);
        return R.ok().message("删除成功");
    }
}
