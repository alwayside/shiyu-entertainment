package com.shiyu.controller;

import com.shiyu.authority.AuthorityCenter;
import com.shiyu.entity.model.AdminDto;
import com.shiyu.entity.repository.AdminDo;
import com.shiyu.service.AdminService;
import com.shiyu.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthorityCenter authorityCenter;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminDo adminDo,
                                   HttpServletRequest request) {
        String token = "";
        AdminDo user = adminService.userLogin(adminDo);
        if (user != null){
            token = TokenUtil.createToken(request, adminDo.getAccount());
            authorityCenter.userLogin(adminDo.getAccount(), token);
            AdminDto result = new AdminDto(user);
            result.setToken(token);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    // 暂不开放 TODO
    @PostMapping("/register")
    public ResponseEntity<?> register() {
        adminService.registerAdmin();
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
