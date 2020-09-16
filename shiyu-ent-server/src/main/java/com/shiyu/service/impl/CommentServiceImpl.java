package com.shiyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shiyu.entity.model.CommentDto;
import com.shiyu.entity.repository.CommentDo;
import com.shiyu.entity.repository.SourceType;
import com.shiyu.mapper.CommentMapper;
import com.shiyu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public PageInfo<CommentDto> searchArticleComment(Long articleId, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<CommentDto> result = commentMapper.selectCommentById(articleId, SourceType.ARTICLE.getValue());
        PageInfo<CommentDto> pageInfo = new PageInfo<>(result);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveArticleComment(Long articleId, CommentDo commentDo) {
        commentDo.setSourceId(articleId);
        commentDo.setSourceType(SourceType.ARTICLE.getValue());
        commentMapper.saveComment(commentDo.getContent(), commentDo.getReply(), commentDo.getSourceId(), commentDo.getSourceType(), commentDo.getUserId());
    }
}
