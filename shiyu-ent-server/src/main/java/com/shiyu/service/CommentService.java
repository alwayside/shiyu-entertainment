package com.shiyu.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shiyu.entity.model.CommentDto;
import com.shiyu.entity.repository.CommentDo;

public interface CommentService {
    PageInfo<CommentDto> searchArticleComment(Long articleId, Page page);

    void saveArticleComment(Long articleId, CommentDo commentDo);

    PageInfo<CommentDto> searchCommentReply(Long commentId, Page page);

    void deleteArticleComment(Long userId, Long commentId);
}
