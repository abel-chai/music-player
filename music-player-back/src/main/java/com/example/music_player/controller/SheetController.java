package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.entity.Sheet;
import com.example.music_player.service.SheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/18 17:48
 **/
@RestController
@RequestMapping("/sheet")
@CrossOrigin
@Api(tags = "歌单接口", description = "歌单获取、更新、删除、搜索、计数等操作。")
public class SheetController {
    @Autowired
    SheetService sheetService;

    /**
     * @Author Gary
     * @Description 获取全部系统歌单
     * @Date 20:17 2022/10/25
     * @Version 1.0
     */
    // TODO 只展示系统歌单
    @GetMapping("/")
    @ApiOperation("获取全部系统歌单")
    public Response getAllSheet() {
        return Response.success("获取成功", sheetService.list());
    }

    /**
     * @Author Gary
     * @Description 获取全部系统歌单（分页）
     * @Date 20:17 2022/10/25
     * @Version 1.0
     */
    // TODO 只展示系统歌单
    @GetMapping("/{numOfPage}/{pageNo}")
    @ApiOperation("获取全部系统歌单（分页）")
    public Response getAllSheetPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo) {
        return sheetService.getAllSheetPage(numOfPage, pageNo);
    }

    /**
     * @Author Gary
     * @Description 更新歌单信息
     * @Date 20:17 2022/10/25
     * @Version 1.0
     */
    @PutMapping("/info")
    @ApiOperation("更新歌单信息")
    public Response updateSheetInfo(Sheet sheet) {
        return sheetService.updateSheetInfo(sheet);
    }

    /**
     * @Author Gary
     * @Description 更新歌单图片
     * @Date 20:17 2022/10/25
     * @Version 1.0
     */
    @PutMapping("/avatar/{id}")
    @ApiOperation("更新歌单图片")
    public Response updateSheetAvatar(@RequestParam("file") MultipartFile avatarFile, @PathVariable("id") int id) {
        return sheetService.updateSheetAvatar(avatarFile, id);
    }

    /**
     * @Author Gary
     * @Description 删除歌单
     * @Date 20:18 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除歌单")
    public Response deleteSheet(@PathVariable("id") int id) {
        return sheetService.deleteSheet(id);
    }

    /**
     * @Author Gary
     * @Description 搜索歌单（通过歌单名字）
     * @Date 20:18 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{name}")
    @ApiOperation("搜索歌单（通过歌单名字）")
    public Response searchSheetByName(@PathVariable("name") String name) {
        return sheetService.searchSheetByName(name);
    }

    /**
     * @Author Gary
     * @Description 搜索歌单（通过歌单名字）（分页）
     * @Date 20:18 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{numOfPage}/{pageNo}/{title}")
    @ApiOperation("搜索歌单（通过歌单名字）（分页）")
    public Response searchSheetByNamePage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("title") String title) {
        return sheetService.searchSheetByNamePage(numOfPage, pageNo, title);         // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 添加歌单
     * @Date 20:20 2022/10/25
     * @Version 1.0
     */
    @PostMapping("/")
    @ApiOperation("添加歌单")
    public Response addSheet(Sheet sheet) {
        return sheetService.addSheet(sheet);
    }

    /**
     * @Author Gary
     * @Description 获取歌单数量
     * @Date 20:20 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/count")
    @ApiOperation("获取歌单数量")
    public Response countSheet() {
        return Response.success("获取成功", sheetService.count());
    }

    /**
     * @Author Gary
     * @Description 搜索歌单（通过风格）
     * @Date 15:33 2022/11/15
     * @Version 1.0
     */
    @GetMapping("/style/{style}")
    @ApiOperation("搜索歌单（通过风格）")
    public Response getSheetByStyle(@PathVariable("style") String style) {
        return sheetService.getSheetByStyle(style);
    }

    /**
     * @Author Gary
     * @Description 搜索歌单（通过风格）（分页）
     * @Date 23:47 2022/10/26
     * @Version 1.0
     */
    @GetMapping("/style/{numOfPage}/{pageNo}/{style}")
    @ApiOperation("搜索歌单（通过风格）（分页）")
    public Response getSheetByStylePage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("style") String style) {
        return sheetService.getSheetByStylePage(numOfPage, pageNo, style);
    }

    /**
     * @Author Abel
     * @Description 在全部歌曲中搜索（通过标题）
     * @Date 21:15 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/{title}")
    @ApiOperation("在全部歌曲中搜索（通过标题）")
    public Response searchSheet(@PathVariable("title") String title) {
        return sheetService.searchSheet(title);
    }

    /**
     * @Author Abel
     * @Description 在全部歌曲中搜索（分页）
     * @Date 21:15 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/{numOfPage}/{pageNo}/{title}")
    @ApiOperation("在全部歌曲中搜索（分页）")
    public Response searchSheetPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("title") String title) {
        return sheetService.searchSheet(numOfPage, pageNo, title);                             // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 获取歌单风格分布
     * @Date 10:23 2022/10/26
     * @Version 1.0
     */
    @GetMapping("/style")
    @ApiOperation("获取歌单风格分布")
    public Response getSheetAllStyleNum() {
        return sheetService.getSheetAllStyleNum();
    }

}
