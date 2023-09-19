package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lys
 * @Date 2023/9/13
 **/
public interface UploadService {

    /**
     * 上传图片
     * @param img
     * @return
     */
    ResponseResult uploadImg(MultipartFile img);
}
