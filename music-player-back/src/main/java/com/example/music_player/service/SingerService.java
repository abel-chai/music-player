package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.Singer;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName SingerService
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:12
 * @Version 1.0
 **/
public interface SingerService extends IService<Singer> {
    Response updateSingerAvatar(MultipartFile avatarFile, int id);

    Response updateSingerInfo(Singer singer);

    Response getSingerOneSexNum(String sex);

    Response getSingerAllSexNum();

    Response getSingerCountryNum();

    Response getSinger(String name);

    Response getSingerPage(Integer numOfPage, Integer pageNo, String name);

    Response addSinger(Singer singer);

    Response deleteSinger(int id);

    Response getAllSingerPage(Integer numOfPage, Integer pageNo);

    Response searchSingerSex(String sex);

    Response searchSingerCountry(String country);
}
