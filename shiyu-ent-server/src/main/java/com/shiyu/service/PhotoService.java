package com.shiyu.service;

import com.shiyu.entity.repository.Photo;
import org.springframework.web.multipart.MultipartFile;

/**
 * photo-manager
 * 2020/8/14 14:43
 *
 * @since
 **/
public interface PhotoService {

    Boolean checkFileForSpringUpload(MultipartFile file);

    void savePhotoLocal(MultipartFile file);

    Photo getPhotoById(Long photoId);
}
