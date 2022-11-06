package com.example.music_player.controller;

import com.example.music_player.common.Response;
import com.example.music_player.entity.User;
import com.example.music_player.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/18 17:49
 **/
@RestController
@RequestMapping("/client")
@CrossOrigin
@Api(tags = "用户接口", description = "包括用户的添加、删除、查看、修改等操作。")
public class ClientController {
    @Autowired
    private UserService userService;

    /**
     * @Author Gary
     * @Description 删除用户信息
     * @Date 20:00 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{cid}")
    @ApiOperation("删除用户信息")
    public Response deleteClientByCid(@PathVariable("cid") Integer cid) {
        return userService.deleteClientByCid(cid);
    }

    /**
     * @Author Gary
     * @Description 获取所有用户信息
     * @Date 20:00 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/")
    @ApiOperation("获取所有用户信息")
    public Response getAllClient() {
        return userService.getAllClient();
    }

    /**
     * @Author Gary
     * @Description 获取所有用户信息（分页）
     * @Date 1:11 2022/10/26
     * @Version 1.0
     */
    @GetMapping("/{numOfPage}/{pageNo}")
    @ApiOperation("获取所有用户信息（分页）")
    public Response getAllClientPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo) {
        return userService.getAllClientPage(numOfPage, pageNo);
    }

    /**
     * @Author Abel
     * @Description 获取单个用户信息
     * @Date 10:24 2022/10/19
     **/
    @GetMapping("/{cid}")
    @ApiOperation("获取单个用户信息")
    public Response getClientByCid(@PathVariable("cid") Integer cid) {
        return userService.getClientByCid(cid);
    }

    /**
     * @Author Abel
     * @Description 用户信息修改
     * @Date 10:24 2022/10/19
     **/
    @PutMapping("/")
    @ApiOperation("用户信息修改")
    public Response updateClientByCid(User client) {
        return userService.updateClientByCid(client);
    }

    /**
     * @Author Gary
     * @Description 搜索用户信息
     * @Date 20:01 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{name}")
    @ApiOperation("搜索用户信息")
    public Response searchClient(@PathVariable("name") String name) {
        return userService.searchClient(name);
    }

    /**
     * @Author Gary
     * @Description 搜索用户信息（分页）
     * @Date 20:01 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/search/{numOfPage}/{pageNo}/{name}")
    @ApiOperation("搜索用户信息（分页）")
    public Response searchClientPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("name") String name) {
        return userService.searchClientPage(numOfPage, pageNo, name);
    }

    /**
     * @Author Gary
     * @Description 获取用户数量
     * @Date 20:02 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/count")
    @ApiOperation("获取用户数量")
    public Response countClient() {
        return userService.countClient();
    }

    /**
     * @Author Abel
     * @Description 用户注册
     * @Date 10:29 2022/10/19
     **/
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Response clientRegister(User client) {
        return userService.clientRegister(client);
    }

    /**
     * @Author Gary
     * @Description 获取用户性别分布信息
     * @Date 10:17 2022/10/26
     * @Version 1.0
     */
    @GetMapping("/sex")
    @ApiOperation("获取用户性别分布信息")
    public Response getClientAllSexNum() {
        return userService.getClientAllSexNum();
    }

}
