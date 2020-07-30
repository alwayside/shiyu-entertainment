package com.shiyu.mapper;

import com.shiyu.entity.repository.AdminDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    void adminRegister(@Param("account")String account, @Param("username")String username, @Param("password")String password);

    AdminDo selectId(int id);

    AdminDo selectAccount(@Param("account") String account);
}
