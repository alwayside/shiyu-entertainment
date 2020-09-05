package com.shiyu.mapper;

import com.shiyu.entity.repository.AlbumDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlbumMapper {

    List<AlbumDo> selectByName(@Param("albumName") String albumName);

    void addAlbum(@Param("userId") Long userId, @Param("albumName") Long albumName);

    void deleteAlbumById(@Param("albumId") Long albumId);

    void deleteAlbumPhotoById(@Param("albumId") Long albumId);

    void saveAlbumCover(@Param("albumId") Long albumId, @Param("photoId") Long photoId);

    void addPhotoToAlbum(@Param("albumId") Long albumId, @Param("photoId") Long photoId);

    List<AlbumDo> selectById(@Param("albumId") Long albumId);

    void updateAlbum(@Param("albumId")Long albumId,@Param("albumName") Long albumName);

    List<AlbumDo> selectByUser(@Param("albumName")String albumName,@Param("userId") Long userId);
}
