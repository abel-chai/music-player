package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.Sheet;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName SongListService
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:23
 * @Version 1.0
 **/
public interface SheetService extends IService<Sheet> {
    Response updateSheetInfo(Sheet sheet);

    Response updateSheetAvatar(MultipartFile avatarFile, int id);

    Response searchSheetByName(String name);

    Response searchSheetByNamePage(Integer numOfPage, Integer pageNo, String title);

    Response getSheetByStyle(String style);

    Response getSheetByStylePage(Integer numOfPage, Integer pageNo, String style);

    Response addSheet(Sheet sheet);

    Response searchSheet(String title);

    Response searchSheet(Integer numOfPage, Integer pageNo, String title);

    Response deleteSheet(int id);

    Response getSheetAllStyleNum();

    Response getAllSheetPage(Integer numOfPage, Integer pageNo);
}
