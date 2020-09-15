package com.shiyu.mapper;

import com.shiyu.entity.model.AlbumDto;
import com.shiyu.entity.repository.AlbumDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlbumMapper {

    List<AlbumDto> selectByName(@Param("albumName") String albumName);

    void addAlbum(@Param("userId") Long userId, @Param("albumName") String albumName);

    void deleteAlbumById(@Param("albumId") Long albumId);

    void deleteAlbumPhotoById(@Param("albumId") Long albumId);

    void saveAlbumCover(@Param("albumId") Long albumId, @Param("photoId") Long photoId);

    void addPhotoToAlbum(@Param("albumId") Long albumId, @Param("photoId") Long photoId);

    List<AlbumDo> selectById(@Param("albumId") Long albumId);

    void updateAlbum(@Param("albumId") Long albumId, @Param("albumName") String albumName);

    List<AlbumDto> selectByUser(@Param("albumName") String albumName, @Param("userId") Long userId);

    void deleteAlbumPhoto(@Param("albumId") Long albumId, @Param("photoId") Long photoId);
}
