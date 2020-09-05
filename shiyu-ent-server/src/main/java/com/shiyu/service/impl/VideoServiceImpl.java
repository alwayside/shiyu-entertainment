package com.shiyu.service.impl;

import com.shiyu.entity.repository.Video;
import com.shiyu.mapper.VideoMapper;
import com.shiyu.service.VideoService;
import com.shiyu.utils.MD5util;
import com.shiyu.utils.VideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * photo-manager
 * 2020/8/25 15:04
 *
 * @since
 **/
@Service
public class VideoServiceImpl implements VideoService {

    private static final String STORE_PATH = "\\store_video";

    @SuppressWarnings("all")
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Video getVideoById(Long videoId) {
        return videoMapper.getVideoById(videoId);
    }

    /**
     * SpringMVC上传：验证上传图片的大小和扩展名
     *
     * @param file 上传文件
     * @return 验证通过返回true，否则返回false
     */
    @Override
    public Boolean checkFileForSpringUpload(MultipartFile file) {
//        if (!PhotoUtil.isPhotoFile(file)) {
//            return false;
//        }
//        if (!Arrays.<String>asList(extMap.get(IMAGE).split(",")).contains(PhotoUtil.getImageType(file))) {
//            logger.error("the Upload file : " + file.getOriginalFilename() + "with an illegal extension name.\nThat's only allow " + extMap.get(IMAGE) + " format.");
//            return false;
//        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveVideoLocal(MultipartFile multipartFile) {
        //封装结果集
        try {
            String md5 = MD5util.getMd5(multipartFile);
            if (videoMapper.checkMd5(md5) != null) {
                return;
            }
            String base = System.getProperty("user.dir");
            String path = base + STORE_PATH + "\\" + VideoUtil.creatPathName(multipartFile);
            File file = VideoUtil.saveFile(multipartFile, path);
            Video video = VideoUtil.getVideoParams(file);
            video.setMd5Code(md5);
            saveVideo(video);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveVideo(Video video) {
        video.setCreateTime(System.currentTimeMillis());
        videoMapper.addVideo(video.getWidth(), video.getHeight(),
                video.getVideoType(), video.getSize(), video.getFileType(),
                video.getCreateTime(), video.getFileName(), video.getPath(),
                video.getMd5Code(), video.getDuration());
    }
}
