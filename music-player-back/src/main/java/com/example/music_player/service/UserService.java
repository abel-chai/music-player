package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.User;

/**
 * @Author:Gary
 * @ProjectName:music_player_test
 * @Date: 2022/10/25 15:56
 **/
public interface UserService extends IService<User> {

    Response addAdmin(User admin);

    Response searchClient(String name);

    Response searchClientPage(Integer numOfPage, Integer pageNo, String name);

    Response getAllClient();

    Response getAllClientPage(Integer numOfPage, Integer pageNo);

    Response countClient();

    Response getAllAdmin();

    Response getAllAdminPage(Integer numOfPage, Integer pageNo);

    Response deleteAdmin(Integer id);

    Response deleteClientByCid(Integer cid);

    Response getClientByCid(Integer cid);

    Response updateClientByCid(User client);

    Response clientRegister(User client);

    Response getClientAllSexNum();

    Response searchAdmin(String name);

    Response searchAdminPage(Integer numOfPage, Integer pageNo, String name);
}
