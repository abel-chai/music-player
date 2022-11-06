package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.service.SheetContainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/19 13:54
 **/
@RestController
@RequestMapping("/sheetContain")
@CrossOrigin
@Api(tags = "歌单与歌曲接口", description = "对歌单与歌曲之间的关系进行操作。")
public class SheetContainController {
    @Autowired
    SheetContainService sheetContainService;

    /**
     * @Author Abel
     * @Description 获取歌单及歌信息（前台）
     * @Date 10:44 2022/10/19
     **/
    @GetMapping("/pre/{sid}")
    @ApiOperation("获取歌单及歌信息（前台）")
    public Response getSheetContainBySid(@PathVariable("sid") Integer sid) {
        return sheetContainService.getSheetContainBySid(sid);
    }

    /**
     * @Author Abel
     * @Description 获取歌单及歌信息（前台）（分页）
     * @Date 10:44 2022/10/19
     **/
    @GetMapping("/pre/{numOfPage}/{pageNo}/{sid}")
    @ApiOperation("获取歌单及歌信息（前台）（分页）")
    public Response getSheetContainBySidPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("sid") Integer sid) {
        return sheetContainService.getSheetContainBySidPage(numOfPage, pageNo, sid);   // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 获取歌单的歌曲（后台）
     * @Date 20:12 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/post/{sheetId}")
    @ApiOperation("获取歌单的歌曲（后台）")
    public Response getBySheet(@PathVariable Integer sheetId) {
        return sheetContainService.getSongBySheet(sheetId);
    }

    /**
     * @Author Gary
     * @Description 获取歌单的歌曲（后台）（分页）
     * @Date 20:12 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/post/{numOfPage}/{pageNo}/{sheetId}")
    @ApiOperation("获取歌单的歌曲（后台）（分页）")
    public Response getBySheetPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable Integer sheetId) {
        return sheetContainService.getSongBySheetPage(numOfPage, pageNo, sheetId);     // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 搜索歌单的歌曲
     * @Date 20:12 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{sheetId}/{songName}")
    @ApiOperation("搜索歌单的歌曲")
    public Response searchBySheet(@PathVariable("sheetId") Integer sheetId, @PathVariable("songName") String songName) {
        return sheetContainService.searchSongBySheet(sheetId, songName);
    }

    /**
     * @Author Gary
     * @Description 搜索歌单的歌曲（分页）
     * @Date 20:12 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{numOfPage}/{pageNo}/{sheetId}/{name}")
    @ApiOperation("搜索歌单的歌曲（分页）")
    public Response searchBySheetPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("sheetId") Integer sheetId, @PathVariable("name") String name) {
        return sheetContainService.searchSongBySheetPage(numOfPage, pageNo, sheetId, name);    // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 删除歌单的歌曲（通过歌单和歌曲的id）
     * @Date 20:13 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{sheetId}/{songId}")
    @ApiOperation("删除歌单的歌曲（通过歌单和歌曲的id）")
    public Response deleteSongInSheet(@PathVariable("sheetId") Integer sheetId, @PathVariable("songId") Integer songId) {
        return sheetContainService.deleteSongInSheet(sheetId, songId);
    }

    /**
     * @Author Gary
     * @Description 删除歌单的歌曲（通过sheet_contains的id）
     * @Date 20:14 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除歌单的歌曲（通过sheet_contains的id）")
    public Response deleteOneSongInSheet(@PathVariable("id") Integer id) {
        return sheetContainService.deleteOneSongInSheet(id);
    }

    /**
     * @Author Gary
     * @Description 添加歌曲到歌单中
     * @Date 20:14 2022/10/25
     * @Version 1.0
     */
    @PostMapping("/{sheetId}/{singerName}/{songName}")
    @ApiOperation("添加歌曲到歌单中")
    public Response addSongInSheet(@PathVariable("sheetId") Integer sheetId, @PathVariable("singerName") String singerName, @PathVariable("songName") String songName) {
        return sheetContainService.addSongInSheet(sheetId, singerName, songName);
    }

    /**
     * @Author Gary
     * @Description 搜索歌单的歌曲（带演唱歌手信息）
     * @Date 21:58 2022/10/31
     * @Version 1.0
     */
    @GetMapping("/search/withSinger/{sheetId}/{name}")
    @ApiOperation("搜索歌单的歌曲（带演唱歌手信息）")
    public Response searchSongSingerBySheet(@PathVariable("sheetId") Integer sheetId, @PathVariable("name") String name) {
        return sheetContainService.searchSongSingerBySheet(sheetId, name);
    }

    /**
     * @Author Gary
     * @Description 搜索歌单的歌曲（带演唱歌手信息）（分页）
     * @Date 21:58 2022/10/31
     * @Version 1.0
     */
    @GetMapping("/search/withSinger/{numOfPage}/{pageNo}/{sheetId}/{name}")
    @ApiOperation("搜索歌单的歌曲（带演唱歌手信息）")
    public Response searchSongSingerBySheetPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("sheetId") Integer sheetId, @PathVariable("name") String name) {
        return sheetContainService.searchSongSingerBySheetPage(numOfPage, pageNo, sheetId, name);
    }
}
