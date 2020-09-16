package com.shiyu.entity.model;

import com.shiyu.entity.repository.CommentDo;

import java.util.List;

public class CommentDto extends CommentDo {

    private Integer comments;

    private String userName;

    private List<CommentDto> replyComment;

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<CommentDto> getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(List<CommentDto> replyComment) {
        this.replyComment = replyComment;
    }
}
