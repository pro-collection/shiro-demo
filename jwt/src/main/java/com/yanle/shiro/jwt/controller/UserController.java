package com.yanle.shiro.jwt.controller;

import com.yanle.shiro.jwt.mapper.UserMapper;
import com.yanle.shiro.jwt.model.ResultMap;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResultMap resultMap;

    @GetMapping("/get-message")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ResultMap getMessage() {
        return resultMap.success().code(200).message("成功获取信息！");
    }

    @PostMapping("/update-password")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ResultMap updatePassword(String username, String oldPassword, String newPassword) {
        String  dataBasePassword = userMapper.getPassword(username);
        if (dataBasePassword.equals(oldPassword)) {
            userMapper.updatePassword(username, newPassword);
        } else {
            return resultMap.fail().message("密码错误！");
        }
        return resultMap.success().code(200).message("成功获得信息");
    }

    @GetMapping("/getVipMessage")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    @RequiresPermissions("vip")
    public ResultMap getVipMessage() {
        return resultMap.success().code(200).message("成功获得 vip 信息");
    }
}
