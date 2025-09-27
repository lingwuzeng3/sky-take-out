package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 包名：com.sky.controller.admin
 * 用户：admin
 * 日期：2025-09-26
 * 项目名称：sky-take-out
 */
@RestController
@Slf4j
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class ConmonController {

    @Autowired
    public AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传:{}",file);
        //文件的字节码 存在云端的uuid名

        try {
            //获取原始文件名，得到文件后缀
            String originalFileName = file.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
            //存在云端的文件名
            String objectName = UUID.randomUUID().toString() + suffix;
            //文件传入云端，并得到文件访问的url
            String url = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(url);

        } catch (IOException e) {
            log.error("文件上传失败:{}",e.getMessage());
            e.printStackTrace();
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
