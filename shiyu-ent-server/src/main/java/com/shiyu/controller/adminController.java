package com.shiyu.controller;

import com.shiyu.entity.repository.AdminDo;
import com.shiyu.service.AdminService;
import com.shiyu.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminDo adminDo,
                                   HttpServletRequest request) {
        String token = "";
        if (adminService.userLogin(adminDo)){
            token = TokenUtil.createToken(request, adminDo.getAccount());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register() {
        adminService.registerAdmin();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
