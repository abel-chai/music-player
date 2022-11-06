package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.SheetMark;

/**
 * @ClassName SongListMarkServcie
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:24
 * @Version 1.0
 **/
public interface SheetMarkService extends IService<SheetMark> {
    Response addMark(Integer cid, Integer sid, String score);

    Response getMark(Integer sid);
}
