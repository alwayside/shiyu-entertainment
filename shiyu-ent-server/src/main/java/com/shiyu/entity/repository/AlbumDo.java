package com.shiyu.entity.repository;

import java.util.Date;

public class AlbumDo {

    private Long albumId;

    private Long albumName;

    private Date dateTime;

    private Long albumCover;

    private Long userId;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getAlbumName() {
        return albumName;
    }

    public void setAlbumName(Long albumName) {
        this.albumName = albumName;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Long getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(Long albumCover) {
        this.albumCover = albumCover;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
