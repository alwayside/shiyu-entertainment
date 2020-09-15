package com.shiyu.entity.model;

import com.shiyu.entity.repository.CommentDo;

public class CommentDto extends CommentDo {

    private Integer comments;

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
