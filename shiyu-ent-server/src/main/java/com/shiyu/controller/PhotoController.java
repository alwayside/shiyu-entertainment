package com.shiyu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shiyu.entity.repository.Photo;
import com.shiyu.service.PhotoService;
import com.shiyu.utils.PhotoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * photo-manager
 * 2020/8/14 14:47
 *
 * @since
 **/
@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    public ResponseEntity<?> photoUploader(MultipartFile file) {
        if (!photoService.checkFileForSpringUpload(file)) {
            throw new RuntimeException("上传" + file.getOriginalFilename());
        }
        photoService.savePhotoLocal(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{photoId}/thumb")
    public void photoThumb(@PathVariable("photoId") Long photoId,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        Photo photo = photoService.getPhotoById(photoId);
        if (photo == null) {
            return;
        }
        BufferedImage image;
        image = PhotoUtil.thumbImage(photo.getPath());
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
        response.getOutputStream().close();
    }

    @GetMapping("/{photoId}/photo")
    public void photo(@PathVariable("photoId") Long photoId,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        Photo photo = photoService.getPhotoById(photoId);
        if (photo == null) {
            return;
        }
        BufferedImage image = ImageIO.read(new File(photo.getPath()));
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
        response.getOutputStream().close();
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<?> getAlbumPhoto(@PathVariable("albumId") Long albumId,
                                           @RequestParam Integer pageNum,
                                           @RequestParam Integer pageSize) {
        PageInfo<Photo> photos = photoService.getPhotoByAlbum(albumId, new Page(pageNum, pageSize));
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }
}
