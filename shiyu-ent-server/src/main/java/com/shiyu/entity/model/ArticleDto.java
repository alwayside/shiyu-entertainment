package com.shiyu.entity.model;

import com.shiyu.entity.repository.ArticleDo;

public class ArticleDto extends ArticleDo {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
