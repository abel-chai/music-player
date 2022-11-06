package com.example.music_player.handler;

import com.example.music_player.common.Response;
import com.example.music_player.utils.SecurityUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/24 16:25
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Response result = Response.error("权限不足");
        SecurityUtils.renderString(response, result);
    }
}
