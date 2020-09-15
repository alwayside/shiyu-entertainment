package com.shiyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shiyu.entity.model.AlbumDto;
import com.shiyu.entity.repository.AlbumDo;
import com.shiyu.entity.repository.Photo;
import com.shiyu.mapper.AlbumMapper;
import com.shiyu.mapper.PhotoMapper;
import com.shiyu.service.AlbumService;
import com.shiyu.utils.MD5util;
import com.shiyu.utils.PhotoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private PhotoMapper photoMapper;


    @Override
    public PageInfo<AlbumDto> searchAlbum(String albumName, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<AlbumDto> result = albumMapper.selectByName(albumName);
        PageInfo<AlbumDto> pageInfo = new PageInfo<>(result);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAlbum(AlbumDo albumDo) {
        if(albumDo.getUserId() == null) {
            throw new RuntimeException("inexistent user");
        }
        albumMapper.addAlbum(albumDo.getUserId(), albumDo.getAlbumName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAlbumById(Long albumId) {
        albumMapper.deleteAlbumPhotoById(albumId);
        albumMapper.deleteAlbumById(albumId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAlbumCover(Long albumId, MultipartFile multipartFile) {
        try {
            String md5 = MD5util.getMd5(multipartFile);
            Long photoId = photoMapper.checkMd5(md5);
            if (photoId != null) {
                albumMapper.saveAlbumCover(albumId, photoId);
                return;
            }
            String base = System.getProperty("user.dir");
            String path = base + PhotoUtil.getStorePath() + "\\" + PhotoUtil.creatPathName(multipartFile);
            File file = PhotoUtil.saveFile(multipartFile, path);
            Photo photo = PhotoUtil.getPhotoParams(file);
            photo.setMd5Code(md5);
            photo.setCreateTime(System.currentTimeMillis());
            photoMapper.addPhoto(photo.getPictureWidth(), photo.getPictureHeight(),
                    photo.getImageType(), photo.getSize(), photo.getFileType(),
                    photo.getCreateTime(), photo.getFileName(), photo.getPath(),
                    photo.getMd5Code());
            photoId = photoMapper.checkMd5(md5);
            albumMapper.saveAlbumCover(albumId, photoId);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPhotoToAlbum(Long albumId, MultipartFile multipartFile) {
        try {
            String md5 = MD5util.getMd5(multipartFile);
            Long photoId = photoMapper.checkMd5(md5);
            if (photoId != null) {
                albumMapper.addPhotoToAlbum(albumId, photoId);
                return;
            }
            String base = System.getProperty("user.dir");
            String path = base + PhotoUtil.getStorePath() + "\\" + PhotoUtil.creatPathName(multipartFile);
            File file = PhotoUtil.saveFile(multipartFile, path);
            Photo photo = PhotoUtil.getPhotoParams(file);
            photo.setMd5Code(md5);
            photo.setCreateTime(System.currentTimeMillis());
            photoMapper.addPhoto(photo.getPictureWidth(), photo.getPictureHeight(),
                    photo.getImageType(), photo.getSize(), photo.getFileType(),
                    photo.getCreateTime(), photo.getFileName(), photo.getPath(),
                    photo.getMd5Code());
            photoId = photoMapper.checkMd5(md5);
            albumMapper.addPhotoToAlbum(albumId, photoId);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAlbum(AlbumDo albumDo) {
        AlbumDo orgAlbum = albumMapper.selectById(albumDo.getAlbumId()).get(0);
        if (orgAlbum == null) {
            return;
        }
        albumMapper.updateAlbum(orgAlbum.getAlbumId(), albumDo.getAlbumName());
    }

    @Override
    public PageInfo<AlbumDto> searchAlbumByUser(String albumName, Long userId, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<AlbumDto> result = albumMapper.selectByUser(albumName, userId);
        PageInfo<AlbumDto> pageInfo = new PageInfo<>(result);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAlbumPhoto(Long albumId, Long photoId) {
        albumMapper.deleteAlbumPhoto(albumId, photoId);
    }

    @Override
    public AlbumDo getAlbumById(Long albumId) {
        return albumMapper.selectById(albumId).get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAlbumCoverPhoto(Long albumId, Long photoId) {
        if (albumMapper.selectById(albumId).size() > 0 && photoMapper.getPhotoById(photoId) != null) {
            albumMapper.saveAlbumCover(albumId, photoId);
        }
    }
}
