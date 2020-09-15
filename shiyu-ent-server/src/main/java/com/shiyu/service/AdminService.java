package com.shiyu.service;

import com.shiyu.entity.repository.AdminDo;
import com.shiyu.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;

    public AdminDo selectAdmin(int id) {
        return adminMapper.selectId(id);
    }

    public AdminDo userLogin(AdminDo adminDo) {
        AdminDo user = adminMapper.selectAccount(adminDo.getAccount());
        String password = adminDo.getPassWord();
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(password.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            password = encoder.encode(md5);
            if (user.getPassWord().equals(password)){
                user.setPassWord(null);
                return user;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public void registerAdmin() {
        String account = "admin";
        String username = "admin";
        String password = "shiyu123";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(password.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            password = encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if ("shiyu123".equals(password)) {
            return;
        }
        adminMapper.adminRegister(account, username, password);
    }

    public void register(AdminDo adminDo) throws Throwable {
        String account = adminDo.getAccount();
        String username = adminDo.getUserName();
        String password = adminDo.getPassWord();
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(password.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            password = encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (adminMapper.selectAccount(account) != null) {
            throw new Throwable("duplicate account");
        }
        adminMapper.adminRegister(account, username, password);
    }
}
