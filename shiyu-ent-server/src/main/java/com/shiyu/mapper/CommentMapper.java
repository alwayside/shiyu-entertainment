package com.shiyu.mapper;

import com.shiyu.entity.model.AlbumDto;
import com.shiyu.entity.model.CommentDto;
import com.shiyu.entity.repository.AlbumDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    List<CommentDto> selectCommentById(@Param("sourceId") Long sourceId, @Param("sourceType") String type);

    void saveComment(@Param("content") String content, @Param("reply") Long reply, @Param("sourceId") Long sourceId, @Param("sourceType") String sourceType, @Param("userId") Long userId);
}
