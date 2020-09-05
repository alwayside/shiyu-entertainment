package com.shiyu.service.impl;

import com.shiyu.entity.repository.Photo;
import com.shiyu.mapper.PhotoMapper;
import com.shiyu.service.PhotoService;
import com.shiyu.utils.MD5util;
import com.shiyu.utils.PhotoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * photo-manager
 * 2020/8/14 14:46
 *
 * @since
 **/
@Component
public class PhotoServiceImpl implements PhotoService {

    @SuppressWarnings("all")
    @Autowired
    private PhotoMapper photoMapper;

    /**
     * 定义允许上传文件扩展名
     */
    private static HashMap<String, String> extMap = new HashMap<String, String>();

    /**
     * 定义图片
     */
    private static final String IMAGE = "image";
    /**
     * 定义视频
     */
    private static final String MEDIA = "media";


    /**
     * 定义图片上传最大限制10MB
     */
    private static long maxSize = 10 * 1024 * 1024;


    private static final String STORE_PATH = "\\store_photo";

    private static Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);

    public PhotoServiceImpl() {
        //允许图片扩展名
        extMap.put(IMAGE, "gif,jpg,jpeg,png,bmp");
        //允许视频扩展名
        extMap.put(MEDIA, "mp4");
    }

    /**
     * SpringMVC上传：验证上传图片的大小和扩展名
     *
     * @param file 上传文件
     * @return 验证通过返回true，否则返回false
     */
    @Override
    public Boolean checkFileForSpringUpload(MultipartFile file) {
        // 检查文件大小
        if (file.getSize() > maxSize) {
            logger.error("the Upload file :" + file.getOriginalFilename() + " is out of the maxSize");
            return false;
        }
        if (!PhotoUtil.isPhotoFile(file)) {
            return false;
        }
        if (!Arrays.<String>asList(extMap.get(IMAGE).split(",")).contains(PhotoUtil.getImageType(file))) {
            logger.error("the Upload file : " + file.getOriginalFilename() + "with an illegal extension name.\nThat's only allow " + extMap.get(IMAGE) + " format.");
            return false;
        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePhotoLocal(MultipartFile multipartFile) {
        //封装结果集
        try {
            String md5 = MD5util.getMd5(multipartFile);
            if (photoMapper.checkMd5(md5) != null) {
                return;
            }
            String base = System.getProperty("user.dir");
            String path = base + STORE_PATH + "\\" + PhotoUtil.creatPathName(multipartFile);
            File file = PhotoUtil.saveFile(multipartFile, path);
            Photo photo = PhotoUtil.getPhotoParams(file);
            photo.setMd5Code(md5);
            savePhoto(photo);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Photo getPhotoById(Long photoId) {
        return photoMapper.getPhotoById(photoId);
    }

    private void savePhoto(Photo photo) {
        photo.setCreateTime(System.currentTimeMillis());
        photoMapper.addPhoto(photo.getPictureWidth(), photo.getPictureHeight(),
                photo.getImageType(), photo.getSize(), photo.getFileType(),
                photo.getCreateTime(), photo.getFileName(), photo.getPath(),
                photo.getMd5Code());
    }
}

