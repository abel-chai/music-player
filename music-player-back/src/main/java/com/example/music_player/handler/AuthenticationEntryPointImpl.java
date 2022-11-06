package com.example.music_player.handler;

import com.example.music_player.common.Response;
import com.example.music_player.utils.SecurityUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/24 16:38
 **/
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Response result = Response.error("认证失败");
        SecurityUtils.renderString(response, result);
    }
}

