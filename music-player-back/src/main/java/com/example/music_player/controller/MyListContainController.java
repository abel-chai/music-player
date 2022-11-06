package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.entity.MyListContain;
import com.example.music_player.service.MyListContainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName MyListContainController
 * @Description
 * @Author Abel
 * @Date 2022/10/18 0:07
 * @Version 1.0
 **/
@RestController
@RequestMapping("/myListContain")
@CrossOrigin
@Api(tags = "播放列表与收藏歌曲接口", description = "对播放列表与歌曲收藏的操作。")
public class MyListContainController {
    @Autowired
    private MyListContainService myListContainService;

    /**
     * @Author Abel
     * @Description 获取播放列表或收藏歌曲
     * @Date 10:45 2022/10/19
     **/
    @GetMapping("/{cid}/{type}")
    @ApiOperation("获取播放列表或收藏歌曲")
    public Response getPlaylistOrLove(@PathVariable("cid") Integer cid, @PathVariable("type") Integer type) {
        return myListContainService.getPlaylistOrLoveByCid(cid, type);
    }

    /**
     * @Author Abel
     * @Description 获取播放列表或收藏歌曲（分页）
     * @Date 10:45 2022/10/19
     **/
    @GetMapping("/{numOfPage}/{pageNo}/{cid}/{type}")
    @ApiOperation("获取播放列表或收藏歌曲（分页）")
    public Response getPlaylistOrLovePage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("cid") Integer cid, @PathVariable("type") Integer type) {
        return myListContainService.getPlaylistOrLoveByCidPage(numOfPage, pageNo, cid, type); //TODO 未测试
    }

    /**
     * @Author Abel
     * @Description 从播放列表删除歌曲或取消收藏歌曲
     * @Date 10:46 2022/10/19
     **/
    @DeleteMapping("/{cid}/{sid}/{type}")
    @ApiOperation("从播放列表删除歌曲或取消收藏歌曲")
    public Response delPlaylistOrLove(@PathVariable("cid") Integer cid, @PathVariable("sid") Integer sid, @PathVariable("type") Integer type) {
        return myListContainService.delPlaylistOrLoveBySid(cid, sid, type);
    }

    @DeleteMapping("/{cid}")
    @ApiOperation("从播放列表删除歌曲或取消收藏歌曲")
    public Response delPlaylist(@PathVariable("cid") Integer cid) {
        return myListContainService.delPlayListByCid(cid);
    }

    /**
     * @Author Abel
     * @Description 添加歌曲到播放列表或收藏歌曲
     * @Date 10:50 2022/10/19
     **/
    @PostMapping("/")
    @ApiOperation("添加歌曲到播放列表或收藏歌曲")
    public Response addSongToPlaylist(MyListContain myListContain) {
        return myListContainService.addSongToPlaylist(myListContain);
    }

    /**
     * @Author Gary
     * @Description 搜索播放列表或收藏歌曲
     * @Date 21:22 2022/10/31
     * @Version 1.0
     */
    @GetMapping("search/{cid}/{type}/{name}")
    @ApiOperation("搜索播放列表或收藏歌曲（通过歌名）")
    public Response searchPlaylistOrLove(@PathVariable("cid") Integer cid, @PathVariable("type") Integer type, @PathVariable("name") String name) {
        return myListContainService.searchPlaylistOrLoveByCid(cid, type, name); //TODO 未测试
    }

    /**
     * @Author Gary
     * @Description 搜索播放列表或收藏歌曲（分页）
     * @Date 21:22 2022/10/31
     * @Version 1.0
     */
    @GetMapping("search/{numOfPage}/{pageNo}/{cid}/{type}/{name}")
    @ApiOperation("搜索播放列表或收藏歌曲（分页）（通过歌名）")
    public Response searchPlaylistOrLovePage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("cid") Integer cid, @PathVariable("type") Integer type, @PathVariable("name") String name) {
        return myListContainService.searchPlaylistOrLoveByCidPage(numOfPage, pageNo, cid, type, name); //TODO 未测试
    }
}
