package com.shiyu.utils;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * media-manager
 * 2020/8/28 10:52
 *
 * @since
 **/
public abstract class MD5util {

    /**
     * 获取文件MD5 码
     * @param file
     * @return
     */
    public static String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            return null;
        }
    }
}
