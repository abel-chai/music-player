package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.entity.Sheet;
import com.example.music_player.service.MySheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SheetCollectedController
 * @Description
 * @Author Abel
 * @Date 2022/10/18 21:38
 * @Version 1.0
 **/
@RestController
@RequestMapping("/mySheet")
@CrossOrigin
@Api(tags = "用户自建歌单接口", description = "对获取、删除自建歌单等操作。")
public class MySheetController {
    @Autowired
    private MySheetService mySheetService;

    /**
     * @Author Abel
     * @Description 获取用户建立的歌单
     * @Date 10:45 2022/10/19
     **/
    @GetMapping("/{cid}")
    @ApiOperation("获取用户建立的歌单")
    public Response getMySheet(@PathVariable("cid") Integer cid) {
        return mySheetService.getMySheetListByCid(cid);
    }

    /**
     * @Author Abel
     * @Description 获取用户建立的歌单（分页）
     * @Date 10:45 2022/10/19
     **/
    @GetMapping("/{numOfPage}/{pageNo}/{cid}")
    @ApiOperation("获取用户建立的歌单（分页）")
    public Response getMySheetPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("cid") Integer cid) {
        return mySheetService.getMySheetListByCidPage(numOfPage, pageNo, cid);
    }

    /**
     * @Author Abel
     * @Description 与sheet下的删除可能重复
     * @Date 23:57 2022/10/18
     **/
    @DeleteMapping("/{sid}")
    @ApiOperation("通过歌单id删除")
    public Response delMySheetBySid(@PathVariable("sid") Integer sid) {
        return mySheetService.delMySheetBySid(sid);
    }

    /**
     * @Author Gary
     * @Description 添加自建歌单
     * @Date 19:15 2022/11/18
     * @Version 1.0
     */
    @PostMapping("/{cid}")
    @ApiOperation("添加自建歌单")
    public Response addSheetBySid(@PathVariable("cid") Integer cid, Sheet sheet) {
        return mySheetService.addSheetBySid(cid, sheet);
    }
}
