package com.shiyu.controller;

import com.shiyu.entity.repository.Video;
import com.shiyu.service.VideoService;
import com.shiyu.utils.PhotoUtil;
import com.shiyu.utils.VideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;


/**
 * photo-manager
 * 2020/8/14 14:47
 *
 * @since
 **/
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/play/{videoId}")
    public void videoPlay(@PathVariable(value = "videoId") Long videoId,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        Video video = videoService.getVideoById(videoId);
        if (video == null) {
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(video.getPath());
            OutputStream outputStream;
            // 获取关联文件的字节数
            int size = fileInputStream.available();
            // 创建缓冲区
            byte[] data = new byte[size];
            fileInputStream.read(data);
            fileInputStream.close();
            // 释放
            fileInputStream = null;
            response.setContentType("video/mp4");
            outputStream = response.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> videoUploader(MultipartFile file) {
        if (!videoService.checkFileForSpringUpload(file)) {
            throw new RuntimeException("上传" + file.getOriginalFilename());
        }
        videoService.saveVideoLocal(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/thumb/{videoId}")
    public void videoThumb(@PathVariable(value = "videoId") Long videoId,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        Video video = videoService.getVideoById(videoId);
        if (video == null) {
            return;
        }
        BufferedImage image;
        image = VideoUtil.getScreenshot(video.getPath());
        image = PhotoUtil.thumbImage(image);
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
        response.getOutputStream().close();
        return;
    }

}
