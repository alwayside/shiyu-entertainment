package com.shiyu.mapper;

import com.shiyu.entity.repository.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * photo-manager
 * 2020/8/21 15:22
 *
 * @since
 **/
@Mapper
public interface PhotoMapper {

    void addPhoto(@Param("pictureWidth") Integer pictureWidth,
                  @Param("pictureHeight") Integer pictureHeight,
                  @Param("imageType") Integer imageType,
                  @Param("size") Long size,
                  @Param("fileType") String fileType,
                  @Param("createTime") Long createTime,
                  @Param("fileName") String fileName,
                  @Param("path") String path,
                  @Param("md5Code") String md5Code);

    Long checkMd5(@Param("md5Code") String md5);

    Photo getPhotoById(@Param("photoId") Long photoId);
}
