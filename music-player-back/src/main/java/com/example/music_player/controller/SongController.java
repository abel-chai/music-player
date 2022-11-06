package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.entity.Song;
import com.example.music_player.service.SongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/18 19:40
 **/
@RestController
@RequestMapping("/song")
@CrossOrigin
@Api(tags = "歌曲接口", description = "歌曲获取、更新、删除、搜索、计数等操作。")
public class SongController {
    @Autowired
    SongService songService;

    /**
     * @Author Gary
     * @Description 获取单个歌曲
     * @Date 20:26 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/{id}")
    @ApiOperation("获取单个歌曲")
    public Response getSongById(@PathVariable("id") Integer id) {
        return songService.getSongById(id);
    }

    /**
     * @Author Gary
     * @Description 更新歌曲图片
     * @Date 20:27 2022/10/25
     * @Version 1.0
     */
    @PutMapping("/avatar/{id}")
    @ApiOperation("更新歌曲图片")
    public Response updateSongAvatar(@RequestParam("file") MultipartFile avatarFile, @PathVariable("id") int id) {
        return songService.updateSongAvatar(avatarFile, id);
    }

    /**
     * @Author Gary
     * @Description 更新歌曲源
     * @Date 20:27 2022/10/25
     * @Version 1.0
     */
    @PutMapping("/music/{id}")
    @ApiOperation("更新歌曲源")
    public Response updateSongSource(@RequestParam("file") MultipartFile musicFile, @PathVariable("id") int id) throws IOException {
        return songService.updateSongSource(musicFile, id);
    }

    /**
     * @Author Gary
     * @Description 添加歌曲（传入歌曲源和歌曲信息）
     * @Date 20:27 2022/10/25
     * @Version 1.0
     */
    @PostMapping("/")
    @ApiOperation("添加歌曲（传入歌曲源和歌曲信息）")
    public Response addSong(@RequestParam("file") MultipartFile file, Song song) {
        return songService.addSong(file, song);
    }

    /**
     * @Author Gary
     * @Description 更新歌曲信息
     * @Date 20:28 2022/10/25
     * @Version 1.0
     */
    @PutMapping("/info")
    @ApiOperation("更新歌曲信息")
    public Response updateSongInfo(Song song) {
        return songService.updateSongInfo(song);
    }

    /**
     * @Author Gary
     * @Description 获取歌手的所有歌曲（通过歌手id）
     * @Date 20:28 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/singer/{singerId}")
    @ApiOperation("取歌手的所有歌曲（通过歌手id）")
    public Response getSongBySingerId(@PathVariable("singerId") Integer singerId) {
        return songService.getSongBySingerId(singerId);
    }

    /**
     * @Author Gary
     * @Description 获取歌手的所有歌曲（通过歌手id）（分页）
     * @Date 20:28 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/singer/{numOfPage}/{pageNo}/{singerId}")
    @ApiOperation("获取歌手的所有歌曲（通过歌手id）（分页）")
    public Response getSongBySingerIdPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("singerId") Integer singerId) {
        return songService.getSongBySingerIdPage(numOfPage, pageNo, singerId);     // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 搜索歌手的歌曲（通过歌手id，关键词为歌名）
     * @Date 20:31 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{singerId}/{name}")
    @ApiOperation("搜索歌手的歌曲（通过歌手id，关键词为歌名）")
    public Response searchSong(@PathVariable("singerId") Integer singerId, @PathVariable("name") String name) {
        return songService.getSong(singerId, name);
    }

    /**
     * @Author Gary
     * @Description 搜索歌手的歌曲（通过歌手id，关键词为歌名）（分页）
     * @Date 20:31 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{numOfPage}/{pageNo}/{singerId}/{name}")
    @ApiOperation("搜索歌手的歌曲（通过歌手id，关键词为歌名）（分页）")
    public Response searchSongPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("singerId") Integer singerId, @PathVariable("name") String name) {
        return songService.getSongPage(numOfPage, pageNo, singerId, name);         // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 搜索歌曲（关键词为歌名）
     * @Date 21:12 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/{name}")
    @ApiOperation("搜索歌曲（关键词为歌名）")
    public Response searchSong(@PathVariable("name") String name) {
        return songService.searchSong(name);
    }

    /**
     * @Author Gary
     * @Description 搜索歌曲（关键词为歌名）（分页）
     * @Date 21:12 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/{numOfPage}/{pageNo}/{name}")
    @ApiOperation("搜索歌曲（关键词为歌名）（分页）")
    public Response searchSongPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("name") String name) {
        return songService.searchSongPage(numOfPage, pageNo, name);                // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 删除歌曲（通过歌曲id）
     * @Date 20:32 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除歌曲（通过歌曲id）")
    public Response deleteSong(@PathVariable("id") int id) {
        return songService.deleteSong(id);
    }

    /**
     * @Author Gary
     * @Description 获取歌曲数量
     * @Date 20:33 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/count")
    @ApiOperation("获取歌曲数量")
    public Response countSong() {
        return Response.success("获取成功", songService.count());
    }

}
