package com.example.music_player.service.impl;

import com.example.music_player.common.Response;
import com.example.music_player.entity.LoginUser;
import com.example.music_player.service.AuthService;
import com.example.music_player.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/22 17:40
 **/
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public Response login(String platform, String name, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(name, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            return Response.error("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        boolean isClient = loginUser.getUser().getAuthority().equals("client");
        if (platform.equals("admin")) {
            if (isClient)
                return Response.error("用户不能在管理员平台登录");
        } else {
            if (!isClient)
                return Response.error("管理员不能在用户平台登录");
        }
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        //authenticate存入redis
        redisTemplate.opsForValue().set("login:" + id, loginUser);
        //把token响应给前端
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        map.put("id", id);

        return Response.success("登陆成功", map);
    }

    @Override
    public Response logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        redisTemplate.delete("login:" + id);
        return Response.success("退出成功");
    }
}
