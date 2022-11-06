package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.entity.Singer;
import com.example.music_player.service.SingerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/18 17:49
 **/
@RestController
@RequestMapping("/singer")
@CrossOrigin
@Api(tags = "歌手接口", description = "歌手获取、更新、删除、搜索、计数等操作。")
public class SingerController {
    @Autowired
    SingerService singerService;

    /**
     * @Author Gary
     * @Description 获取全部歌手
     * @Date 20:21 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/")
    @ApiOperation("获取全部歌手")
    public Response getAllSinger() {
        return Response.success("获取成功", singerService.list());
    }

    /**
     * @Author Gary
     * @Description 获取全部歌手（分页）
     * @Date 20:21 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/{numOfPage}/{pageNo}")
    @ApiOperation("获取全部歌手（分页）")
    public Response getAllSingerPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo) {
        return singerService.getAllSingerPage(numOfPage, pageNo);      // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 更新歌手信息
     * @Date 20:21 2022/10/25
     * @Version 1.0
     */
    @PutMapping("/info")
    @ApiOperation("更新歌手信息")
    public Response updateSingerInfo(Singer singer) {
        return singerService.updateSingerInfo(singer);
    }

    /**
     * @Author Gary
     * @Description 更新歌手图片
     * @Date 20:22 2022/10/25
     * @Version 1.0
     */
    @PutMapping("/avatar/{id}")
    @ApiOperation("更新歌手图片")
    public Response updateSingerAvatar(@RequestParam("file") MultipartFile avatarFile, @PathVariable("id") int id) {
        return singerService.updateSingerAvatar(avatarFile, id);
    }

    /**
     * @Author Gary
     * @Description 删除歌手
     * @Date 20:22 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除歌手")
    public Response deleteSinger(@PathVariable("id") int id) {
        return singerService.deleteSinger(id);
    }

    /**
     * @Author Gary
     * @Description 添加歌手
     * @Date 20:23 2022/10/25
     * @Version 1.0
     */
    @PostMapping("/")
    @ApiOperation("添加歌手")
    public Response addSinger(Singer singer) {
        return singerService.addSinger(singer);
    }

    /**
     * @Author Gary
     * @Description 获取单一性别歌手数量（传入性别）
     * @Date 20:23 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/sex/{sex}")
    @ApiOperation("获取单一性别歌手数量（传入性别）")
    public Response getSingerOneSexNum(@PathVariable("sex") String sex) {
        return singerService.getSingerOneSexNum(sex);
    }

    /**
     * @Author Gary
     * @Description 获取歌手性别分布信息
     * @Date 20:24 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/sex")
    @ApiOperation("获取歌手性别分布信息")
    public Response getSingerAllSexNum() {
        return singerService.getSingerAllSexNum();
    }

    /**
     * @Author Gary
     * @Description 获取歌手地区分布信息
     * @Date 20:25 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/country")
    @ApiOperation("获取歌手地区分布信息")
    public Response getSingerCountryNum() {
        return singerService.getSingerCountryNum();
    }

    /**
     * @Author Gary
     * @Description 搜索歌手（通过歌手姓名）
     * @Date 20:25 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{name}")
    @ApiOperation("搜索歌手（通过歌手姓名）")
    public Response searchSinger(@PathVariable("name") String name) {
        return singerService.getSinger(name);
    }

    /**
     * @Author Gary
     * @Description 搜索歌手（通过歌手姓名）（分页）
     * @Date 20:25 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{numOfPage}/{pageNo}/{name}")
    @ApiOperation("搜索歌手（通过歌手姓名）（分页）")
    public Response searchSingerPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("name") String name) {
        return singerService.getSingerPage(numOfPage, pageNo, name);       // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 获取全部歌手数量
     * @Date 20:25 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/count")
    @ApiOperation("获取全部歌手数量")
    public Response countSinger() {
        return Response.success("获取成功", singerService.count());
    }
}
