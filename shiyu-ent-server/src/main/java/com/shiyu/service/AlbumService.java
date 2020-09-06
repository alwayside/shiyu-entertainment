package com.shiyu.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shiyu.entity.repository.AlbumDo;
import org.springframework.web.multipart.MultipartFile;

public interface AlbumService {

    PageInfo<AlbumDo> searchAlbum(String albumName, Page page);

    void saveAlbum(AlbumDo albumDo);

    void deleteAlbumById(Long albumId);

    void setAlbumCover(Long albumId, MultipartFile file);

    void addPhotoToAlbum(Long albumId, MultipartFile file);

    void updateAlbum(AlbumDo albumDo);

    PageInfo<AlbumDo> searchAlbumByUser(String albumName, Long userId, Page page);

    void deleteAlbumPhoto(Long albumId, Long photoId);

    AlbumDo getAlbumById(Long albumId);

    void setAlbumCoverPhoto(Long albumId, Long photoId);
}
