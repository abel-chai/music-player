package com.example.music_player.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music_player.common.Response;
import com.example.music_player.entity.SheetComment;

/**
 * @ClassName SongLIstComment
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:21
 * @Version 1.0
 **/
public interface SheetCommentService extends IService<SheetComment> {
    Response getCommentBySheet(Integer sheetId);

    Response getCommentBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId);

    Response searchCommentBySheet(Integer sheetId, String clientName);

    Response searchCommentBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId, String clientName);

    Response deleteComment(Integer sheetId, Integer clientId);

    Response deleteOneComment(Integer id);

    Response addComment(SheetComment comment);
}
