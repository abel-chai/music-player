package com.example.music_player.service;

import com.example.music_player.common.Response;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/22 17:40
 **/
public interface AuthService {

    Response login(String platform, String name, String password);

    Response logout();
}
