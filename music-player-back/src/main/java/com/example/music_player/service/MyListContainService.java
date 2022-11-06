package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.MyListContain;

/**
 * @ClassName MyListContainService
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:11
 * @Version 1.0
 **/
public interface MyListContainService extends IService<MyListContain> {

    Response getPlaylistOrLoveByCid(Integer cid, Integer type);

    Response getPlaylistOrLoveByCidPage(Integer numOfPage, Integer pageNo, Integer cid, Integer type);

    Response delPlaylistOrLoveBySid(Integer cid, Integer sid, Integer type);

    Response delPlayListByCid(Integer cid);

    Response addSongToPlaylist(MyListContain myListContain);

    Response searchPlaylistOrLoveByCid(Integer cid, Integer type, String name);

    Response searchPlaylistOrLoveByCidPage(Integer numOfPage, Integer pageNo, Integer cid, Integer type, String name);
}
