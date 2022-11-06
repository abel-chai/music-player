package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/22 16:43
 **/
@RestController
@RequestMapping("/auth")
@CrossOrigin
@Api(tags = "认证接口", description = "包括用户、管理员的登录、退出等操作。")
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * @Author Gary
     * @Description 登录
     * @Date 20:34 2022/10/25
     * @Version 1.0
     */
    @PostMapping(value = "/login")
    @ApiOperation("登录")
    public Response login(HttpServletRequest request, String name, String password) {
        return authService.login(request.getHeader("platform"), name, password);
    }

    /**
     * @Author Gary
     * @Description 注销
     * @Date 20:34 2022/10/25
     * @Version 1.0
     */
    @GetMapping(value = "/logout")
    @ApiOperation("注销")
    public Response logout() {
        return authService.logout();
    }

}
