package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName SongService
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:29
 * @Version 1.0
 **/
public interface SongService extends IService<Song> {
    Response updateSongAvatar(MultipartFile avatarFile, int id);

    Response updateSongSource(MultipartFile musicFile, int id) throws IOException;

    Response updateSongInfo(Song song);

    Response getSongBySingerId(Integer singerId);

    Response getSongSingerBySingerId(Integer singerId);

    Response getSongBySingerIdPage(Integer numOfPage, Integer pageNo, Integer singerId);

    Response getSong(Integer singerId, String name);

    Response getSongPage(Integer numOfPage, Integer pageNo, Integer singerId, String name);

    Response addSong(MultipartFile musicFile, Song song);

    Response getSongSinger(Integer sid);

    Response searchSong(String name);

    Response getSongById(Integer id);

    Response deleteSong(int id);

    Response searchSongPage(Integer numOfPage, Integer pageNo, String name);

    Response getSongSingerLikeName(Integer sid, String name);

    Response getTenSong();
}
