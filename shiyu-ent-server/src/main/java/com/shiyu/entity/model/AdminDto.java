package com.shiyu.entity.model;

import com.shiyu.entity.repository.AdminDo;

public class AdminDto extends AdminDo {

    private String token;

    public AdminDto() {}

    public AdminDto(AdminDo adminDo) {
        this.setAccount(adminDo.getAccount());
        this.setUserName(adminDo.getUserName());
        this.setId(adminDo.getId());
        this.setPassWord(null);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
