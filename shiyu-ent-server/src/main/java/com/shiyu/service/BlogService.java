package com.shiyu.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shiyu.entity.repository.ArticleDo;

import java.util.List;

public interface BlogService {
    void saveArticle(ArticleDo articleDo);

    PageInfo<ArticleDo> searchArticleByTitle(String title, Page page);

    List<ArticleDo> searchArticleById(Long id);

    void deleteArticleById(Long id);

    void updateArticle(ArticleDo articleDo);
}
