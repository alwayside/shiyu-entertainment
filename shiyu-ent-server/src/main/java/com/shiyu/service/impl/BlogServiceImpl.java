package com.shiyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shiyu.entity.model.ArticleDto;
import com.shiyu.entity.repository.ArticleDo;
import com.shiyu.mapper.ArticleMapper;
import com.shiyu.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveArticle(ArticleDo articleDo) {
        if(articleDo.getTitle() == null || articleDo.getTitle().isEmpty()) {
            throw new RuntimeException("no title");
        }
        articleMapper.addArticle(articleDo.getTitle(), articleDo.getContent(), articleDo.getDesc(), articleDo.getId());
    }

    @Override
    public PageInfo<ArticleDto> searchArticleByTitle(String title, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ArticleDto> result = articleMapper.selectByTitle(title);
        PageInfo<ArticleDto> pageInfo = new PageInfo<>(result);
        return pageInfo;
    }

    @Override
    public List<ArticleDto> searchArticleById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticleById(Long id) {
        articleMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(ArticleDo articleDo) {
        ArticleDo orgArticleDo = articleMapper.selectById(articleDo.getId()).get(0);
        if (orgArticleDo == null) {
            return;
        }
        articleMapper.updateArticle(articleDo.getId(), articleDo.getContent(), articleDo.getTitle());
    }
}
