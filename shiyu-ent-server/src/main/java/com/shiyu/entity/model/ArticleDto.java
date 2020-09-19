package com.shiyu.entity.model;

import com.shiyu.entity.repository.ArticleDo;

public class ArticleDto extends ArticleDo {

    private String userName;

    private Integer comments;

    public String getUserName() {
        return userName;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
