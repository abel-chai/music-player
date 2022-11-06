package com.example.music_player.utils;

import com.alibaba.fastjson.JSON;
import com.example.music_player.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/22 17:36
 **/
@Slf4j
public class SecurityUtils {

    public static void renderString(HttpServletResponse response, Response result) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}