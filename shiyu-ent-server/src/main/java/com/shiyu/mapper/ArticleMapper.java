package com.shiyu.mapper;

import com.shiyu.entity.model.ArticleDto;
import com.shiyu.entity.repository.ArticleDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {

    void addArticle(@Param("title") String title,@Param("content") String content,@Param("desc")  String desc, @Param("userId") Long userId);

    List<ArticleDto> selectByTitle(@Param("title") String title);

    List<ArticleDto> selectById(@Param("id") Long id);

    void deleteById(@Param("id")Long id);

    void updateArticle(@Param("id")Long id, @Param("content") String content,@Param("title") String title);

    List<ArticleDto> selectByUser(@Param("title")String title,@Param("userId") Long userId);
}
