package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.LoginUser;
import com.example.music_player.entity.User;
import com.example.music_player.mapper.UserMapper;
import com.example.music_player.service.UserService;
import com.example.music_player.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Gary
 * @ProjectName:music_player_test
 * @Date: 2022/10/25 15:56
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response addAdmin(User admin) {
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username", admin.getUsername())) > 0)
            return Response.error("用户名重复，添加失败");
        admin.setPassword(SecurityUtils.encryptPassword(admin.getPassword()));
        admin.setAuthority("normal_admin");
        if (userMapper.insert(admin) > 0)
            return Response.success("添加成功");
        return Response.error("添加失败");
    }

    @Override
    public Response searchClient(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority", "client").like("username", name);
        return Response.success("搜索成功", userMapper.selectList(queryWrapper));
    }

    @Override
    public Response searchClientPage(Integer numOfPage, Integer pageNo, String name) {
        Page<User> clientPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority", "client").like("username", name);
        userMapper.selectPage(clientPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("clientList", clientPage.getRecords());
        map.put("totalNum", clientPage.getTotal());
        map.put("pageNum", clientPage.getPages());
        map.put("hasPrevious", clientPage.hasPrevious());
        map.put("hasNext", clientPage.hasNext());
        return Response.success("查询成功", map);
    }

    @Override
    public Response getAllClient() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority", "client");
        return Response.success("获取成功", userMapper.selectList(queryWrapper));
    }

    @Override
    public Response getAllClientPage(Integer numOfPage, Integer pageNo) {
        Page<User> userPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority", "client");
        userMapper.selectPage(userPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("clientList", userPage.getRecords());
        map.put("totalNum", userPage.getTotal());
        map.put("pageNum", userPage.getPages());
        map.put("hasPrevious", userPage.hasPrevious());
        map.put("hasNext", userPage.hasNext());
        return Response.success("搜索成功", map);
    }

    @Override
    public Response countClient() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority", "client");
        return Response.success("计数成功", userMapper.selectCount(queryWrapper));
    }

    @Override
    public Response getAllAdmin() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority", "super_admin").or().eq("authority", "normal_admin");
        return Response.success("获取成功", userMapper.selectList(queryWrapper));
    }

    @Override
    public Response getAllAdminPage(Integer numOfPage, Integer pageNo) {
        Page<User> adminPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority", "super_admin").or().eq("authority", "normal_admin");
        userMapper.selectPage(adminPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("adminList", adminPage.getRecords());
        map.put("totalNum", adminPage.getTotal());
        map.put("pageNum", adminPage.getPages());
        map.put("hasPrevious", adminPage.hasPrevious());
        map.put("hasNext", adminPage.hasNext());
        return Response.success("获取成功", map);
    }

    @Override
    public Response deleteAdmin(Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.and(wrapper -> wrapper.eq("authority", "super_admin")
                .or()
                .eq("authority", "normal_admin"));
        if (userMapper.delete(queryWrapper) > 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response deleteClientByCid(Integer cid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", cid).eq("authority", "client");
        if (userMapper.delete(queryWrapper) > 0)
            return Response.success("删除成功");
        return Response.success("删除失败");
    }

    @Override
    public Response getClientByCid(Integer cid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", cid).eq("authority", "client");
        return Response.success("获取成功", userMapper.selectOne(queryWrapper));
    }

    @Override
    public Response updateClientByCid(User client) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", client.getId()).eq("authority", "client");
        if (userMapper.selectOne(queryWrapper) == null)
            return Response.error("更新失败");
        if (userMapper.updateById(client) > 0)
            return Response.error("更新成功");
        return Response.error("更新失败");
    }

    @Override
    public Response clientRegister(User client) {
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username", client.getUsername())) > 0)
            return Response.error("用户名重复，添加失败");
        client.setPassword(SecurityUtils.encryptPassword(client.getPassword()));
        client.setImg("/img/clientImg/clientImg1.png");
        if (userMapper.insert(client) > 0)
            return Response.success("注册成功");
        return Response.success("注册失败");
    }

    @Override
    public Response getClientAllSexNum() {
        List<Map<String, Object>> result = new ArrayList<>();

        Map<String, Object> manMap = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority", "client").like("sex", "男");
        manMap.put("name", "男");
        manMap.put("value", userMapper.selectCount(queryWrapper));
        result.add(manMap);

        queryWrapper.clear();
        queryWrapper.eq("authority", "client").like("sex", "女");
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("name", "女");
        womanMap.put("value", userMapper.selectCount(queryWrapper));
        result.add(womanMap);

        return Response.success("获取成功", result);
    }

    @Override
    public Response searchAdmin(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", name)
                .and(wrapper -> wrapper.eq("authority", "super_admin")
                .or()
                .eq("authority", "normal_admin"));
        return Response.success("搜索成功", userMapper.selectList(queryWrapper));
    }

    @Override
    public Response searchAdminPage(Integer numOfPage, Integer pageNo, String name) {
        Page<User> adminPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", name)
                .and(wrapper -> wrapper.eq("authority", "super_admin")
                .or()
                .eq("authority", "normal_admin"));
        userMapper.selectPage(adminPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("adminList", adminPage.getRecords());
        map.put("totalNum", adminPage.getTotal());
        map.put("pageNum", adminPage.getPages());
        map.put("hasPrevious", adminPage.hasPrevious());
        map.put("hasNext", adminPage.hasNext());
        return Response.success("查询成功", map);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username", username);
        User user = userMapper.selectOne(userWrapper);  //从数据库根据用户名获取密码

        if (user == null)
            throw new UsernameNotFoundException("登录失败，用户名或密码错误！");

        String role;
        if (user.getAuthority().equals("super_admin"))
            role = "ROLE_super_admin";
        else if (user.getAuthority().equals("normal_admin"))
            role = "ROLE_normal_admin";
        else
            role = "ROLE_client";

        return new LoginUser(user, role);
    }
}
