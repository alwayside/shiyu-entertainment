package com.shiyu.service;

import com.shiyu.entity.repository.Video;
import org.springframework.web.multipart.MultipartFile;

/**
 * photo-manager
 * 2020/8/25 15:04
 *
 * @since
 **/
public interface VideoService {

    Video getVideoById(Long videoId);

    Boolean checkFileForSpringUpload(MultipartFile file);

    void saveVideoLocal(MultipartFile file);
}
