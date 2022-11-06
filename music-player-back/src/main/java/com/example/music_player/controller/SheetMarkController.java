package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.service.SheetMarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:Gary
 * @ProjectName:music_player_test
 * @Date: 2022/10/27 13:13
 **/
@RestController
@RequestMapping("/sheetMark")
@Api(tags = "用户打分歌单接口", description = "添加、查看歌单评分等操作。")
public class SheetMarkController {

    @Autowired
    SheetMarkService sheetMarkService;

    /**
     * @Author Gary
     * @Description 添加评分
     * @Date 13:48 2022/10/27
     * @Version 1.0
     */
    @PostMapping("/{cid}/{sid}/{score}")
    @ApiOperation("添加评分（通过用户id、歌单id）")
    public Response addMark(@PathVariable("cid") Integer cid, @PathVariable("sid") Integer sid, @PathVariable("score") String score) {
        return sheetMarkService.addMark(cid, sid, score);
    }

    /**
     * @Author Gary
     * @Description 查看评分
     * @Date 13:49 2022/10/27
     * @Version 1.0
     */
    @GetMapping("/{sid}")
    @ApiOperation("查看评分（通过歌单id）")
    public Response getMark(@PathVariable("sid") Integer sid) {
        return sheetMarkService.getMark(sid);
    }
}
