package com.shiyu.mapper;

import com.shiyu.entity.repository.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * photo-manager
 * 2020/8/21 15:22
 *
 * @since
 **/
@Mapper
public interface VideoMapper {

    void addVideo(@Param("width") Integer width,
                  @Param("height") Integer height,
                  @Param("videoType") Integer videoType,
                  @Param("size") Long size,
                  @Param("fileType") String fileType,
                  @Param("createTime") Long createTime,
                  @Param("fileName") String fileName,
                  @Param("path") String path,
                  @Param("md5Code") String md5Code,
                  @Param("duration") Long duration);

    Long checkMd5(@Param("md5Code") String md5);


    Video getVideoById(@Param("videoId") Long videoId);
}
