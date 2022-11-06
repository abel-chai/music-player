package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.entity.SheetComment;
import com.example.music_player.service.SheetCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/19 0:32
 **/
@RestController
@RequestMapping("/sheetComment")
@CrossOrigin
@Api(tags = "用户评论歌单接口", description = "获取、搜索、删除自建歌单等操作。")
public class SheetCommentController {
    @Autowired
    SheetCommentService sheetCommentService;

    /**
     * @Author Gary
     * @Description 获取歌单的评论
     * @Date 20:06 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/{sheetId}")
    @ApiOperation("获取歌单的评论")
    public Response getCommentBySheet(@PathVariable("sheetId") Integer sheetId) {
        return sheetCommentService.getCommentBySheet(sheetId);
    }

    /**
     * @Author Gary
     * @Description 获取歌单的评论（分页）
     * @Date 20:06 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/{numOfPage}/{pageNo}/{sheetId}")
    @ApiOperation("获取歌单的评论（分页）")
    public Response getCommentBySheetPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("sheetId") Integer sheetId) {
        return sheetCommentService.getCommentBySheetPage(numOfPage, pageNo, sheetId);  // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 搜索歌单的评论（通过用户姓名搜索）
     * @Date 20:07 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{sheetId}/{clientName}")
    @ApiOperation("搜索歌单的评论（通过用户姓名搜索）")
    public Response searchCommentBySheet(@PathVariable("sheetId") Integer sheetId, @PathVariable("clientName") String clientName) {
        return sheetCommentService.searchCommentBySheet(sheetId, clientName);
    }

    /**
     * @Author Gary
     * @Description 搜索歌单的评论（通过用户姓名搜索）（分页）
     * @Date 20:07 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{numOfPage}/{pageNo}/{sheetId}/{clientName}")
    @ApiOperation("搜索歌单的评论（通过用户姓名搜索）（分页）")
    public Response searchCommentBySheetPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("sheetId") Integer sheetId, @PathVariable("clientName") String clientName) {
        return sheetCommentService.searchCommentBySheetPage(numOfPage, pageNo, sheetId, clientName); // TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 删除歌单的评论（通过歌单和用户的id）
     * @Date 20:09 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{sheetId}/{clientId}")
    @ApiOperation("删除歌单的评论（通过歌单和用户的id）")
    public Response deleteComment(@PathVariable("sheetId") Integer sheetId, @PathVariable("clientId") Integer clientId) {
        return sheetCommentService.deleteComment(sheetId, clientId);
    }

    /**
     * @Author Gary
     * @Description 删除歌单的评论（通过评论的id）
     * @Date 20:11 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除歌单的评论（通过评论的id）")
    public Response deleteOneComment(@PathVariable("id") Integer id) {
        return sheetCommentService.deleteOneComment(id);
    }

    /**
     * @Author Gary
     * @Description 添加评论
     * @Date 13:20 2022/10/27
     * @Version 1.0
     */
    @PostMapping("/")
    @ApiOperation("添加歌单的评论（通过用户id、歌单id）")
    public Response addComment(SheetComment comment) {
        return sheetCommentService.addComment(comment);   // TODO 未测试
    }
}
