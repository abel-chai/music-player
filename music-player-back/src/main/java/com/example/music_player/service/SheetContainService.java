package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.SheetContain;

/**
 * @ClassName SongListContainService
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:15
 * @Version 1.0
 **/
public interface SheetContainService extends IService<SheetContain> {

    Response getSongBySheet(Integer sheetId);

    Response getSongBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId);

    Response searchSongBySheet(Integer sheetId, String name);

    Response searchSongBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId, String name);

    Response deleteSongInSheet(Integer sheetId, Integer songId);

    Response addSongInSheet(Integer sheetId, String singerName, String songName);

    Response getSheetContainBySid(Integer sid);

    Response getSheetContainBySidPage(Integer numOfPage, Integer pageNo, Integer sid);

    Response deleteOneSongInSheet(Integer id);

    Response searchSongSingerBySheet(Integer sheetId, String name);

    Response searchSongSingerBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId, String name);
}
