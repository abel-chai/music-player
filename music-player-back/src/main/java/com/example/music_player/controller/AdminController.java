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
 * @Date: 2022/10/18 15:43
 **/
@RestController
@RequestMapping("/admin")
@CrossOrigin
@Api(tags = "管理员接口", description = "包括管理员的添加、删除、查看等操作。")
public class AdminController {
    @Autowired
    private UserService userService;

    /**
     * @Author Gary
     * @Description 添加普通管理员
     * @Date 20:35 2022/10/25
     * @Version 1.0
     */
    @PostMapping("/")
    @ApiOperation("添加普通管理员")
    public Response addAdmin(User admin) {
        return userService.addAdmin(admin);
    }

    /**
     * @Author Gary
     * @Description 删除普通管理员
     * @Date 20:35 2022/10/25
     * @Version 1.0
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除普通管理员")
    public Response deleteAdmin(@PathVariable("id") Integer id) {
        return userService.deleteAdmin(id);
    }

    /**
     * @Author Gary
     * @Description 得到全部普通管理员的信息
     * @Date 20:38 2022/10/25
     * @Version 1.0
     */
    @GetMapping("/")
    @ApiOperation("得到全部普通管理员的信息")
    public Response getAllAdmin() {
        return userService.getAllAdmin();
    }

    /**
     * @Author Gary
     * @Description 得到全部普通管理员的信息（分页）
     * @Date 1:00 2022/10/26
     * @Version 1.0
     */
    @GetMapping("/{numOfPage}/{pageNo}")
    @ApiOperation("得到全部普通管理员的信息（分页）")
    public Response getAllAdminPage(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo) {
        return userService.getAllAdminPage(numOfPage, pageNo);
    }

    /**
     * @Author Gary
     * @Description 搜索管理员
     * @Date 15:12 2022/10/27
     * @Version 1.0
     */
    @GetMapping("/search/{name}")
    @ApiOperation("搜索管理员（通过名字）")
    public Response searchAdmin(@PathVariable("name") String name) {
        return userService.searchAdmin(name);
    }

    /**
     * @Author Gary
     * @Description 搜索管理员（分页）
     * @Date 15:12 2022/10/27
     * @Version 1.0
     */
    @GetMapping("/search/{numOfPage}/{pageNo}/{name}")
    @ApiOperation("搜索管理员（通过名字）（分页）")
    public Response searchAdmin(@PathVariable("numOfPage") Integer numOfPage, @PathVariable("pageNo") Integer pageNo, @PathVariable("name") String name) {
        return userService.searchAdminPage(numOfPage, pageNo, name);
    }
}
