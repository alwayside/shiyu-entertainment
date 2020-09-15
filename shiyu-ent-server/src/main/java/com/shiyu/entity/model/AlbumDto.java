package com.shiyu.entity.model;

import com.shiyu.entity.repository.AlbumDo;
import com.shiyu.entity.repository.Photo;

public class AlbumDto extends AlbumDo {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
