package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.MySheet;

/**
 * @ClassName SongListCollectedService
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:20
 * @Version 1.0
 **/
public interface MySheetService extends IService<MySheet> {
    Response getMySheetListByCid(Integer cid);

    Response delMySheetBySid(Integer sid);

    Response getMySheetListByCidPage(Integer numOfPage, Integer pageNo, Integer cid);
}
