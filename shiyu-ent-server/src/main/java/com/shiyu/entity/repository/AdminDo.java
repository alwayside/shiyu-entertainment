package com.shiyu.entity.repository;

public class AdminDo {
    private int id;
    private String account;
    private String userName;
    private String passWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "AdminDto{" +
                "id=" + this.id +
                ", account='" + this.account + '\'' +
                ", userName='" + this.userName + '\'' +
                ", passWord='" + this.passWord + '\'' +
                '}';
    }
}
