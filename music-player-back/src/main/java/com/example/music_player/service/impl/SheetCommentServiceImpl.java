package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.SheetComment;
import com.example.music_player.entity.User;
import com.example.music_player.mapper.SheetCommentMapper;
import com.example.music_player.mapper.UserMapper;
import com.example.music_player.service.SheetCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName SongLIstCommentServiceImpl
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:22
 * @Version 1.0
 **/
@Service
public class SheetCommentServiceImpl extends ServiceImpl<SheetCommentMapper, SheetComment> implements SheetCommentService {
    @Autowired
    SheetCommentMapper sheetCommentMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Response getCommentBySheet(Integer sheetId) {
        List<Map<String, Object>> result = new ArrayList<>();
        QueryWrapper<SheetComment> sheetCommentWrapper = new QueryWrapper<>();
        sheetCommentWrapper.eq("sheet_id", sheetId);
        List<SheetComment> commentList = sheetCommentMapper.selectList(sheetCommentWrapper);
        commentList.forEach((x) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("client", userMapper.selectById(x.getClientId()));
            map.put("contentId", x.getId());
            map.put("content", x.getContent());
            result.add(map);
        });
        return Response.success("获取成功", result);
    }

    @Override
    public Response getCommentBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId) {
        List<Map<String, Object>> result = new ArrayList<>();
        QueryWrapper<SheetComment> sheetCommentWrapper = new QueryWrapper<>();
        sheetCommentWrapper.eq("sheet_id", sheetId);
        List<SheetComment> commentList = sheetCommentMapper.selectList(sheetCommentWrapper);
        AtomicInteger iter = new AtomicInteger(1);
        commentList.forEach((x) -> {
            if (iter.get() > numOfPage * (pageNo - 1) && iter.get() <= numOfPage * pageNo) {
                Map<String, Object> map = new HashMap<>();
                map.put("client", userMapper.selectById(x.getClientId()));
                map.put("contentId", x.getId());
                map.put("content", x.getContent());
                result.add(map);
            }
            iter.getAndIncrement();
        });
        return Response.success("获取成功", result);
    }

    @Override
    public Response searchCommentBySheet(Integer sheetId, String clientName) {
        List<Map<String, Object>> result = new ArrayList<>();
        QueryWrapper<SheetComment> sheetCommentWrapper = new QueryWrapper<>();
        sheetCommentWrapper.eq("sheet_id", sheetId);
        List<SheetComment> commentList = sheetCommentMapper.selectList(sheetCommentWrapper);
        QueryWrapper<User> clientWrapper = new QueryWrapper<>();
        commentList.forEach((x) -> {
            clientWrapper.clear();
            clientWrapper.eq("id", x.getClientId())
                    .eq("authority", "client")
                    .like("username", clientName);
            User tempClient = userMapper.selectOne(clientWrapper);
            Map<String, Object> map = new HashMap<>();
            if (tempClient != null) {
                map.put("client", tempClient);
                map.put("contentId", x.getId());
                map.put("content", x.getContent());
                result.add(map);
            }
        });
        return Response.success("搜索成功", result);
    }

    @Override
    public Response searchCommentBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId, String clientName) {
        List<Map<String, Object>> result = new ArrayList<>();
        QueryWrapper<SheetComment> sheetCommentWrapper = new QueryWrapper<>();
        sheetCommentWrapper.eq("sheet_id", sheetId);
        List<SheetComment> commentList = sheetCommentMapper.selectList(sheetCommentWrapper);
        QueryWrapper<User> clientWrapper = new QueryWrapper<>();
        AtomicInteger iter = new AtomicInteger(1);
        commentList.forEach((x) -> {
            clientWrapper.clear();
            clientWrapper.eq("id", x.getClientId())
                    .eq("authority", "client")
                    .like("username", clientName);
            User tempClient = userMapper.selectOne(clientWrapper);
            Map<String, Object> map = new HashMap<>();
            if (tempClient != null) {
                if (iter.get() > numOfPage * (pageNo - 1) && iter.get() <= numOfPage * pageNo) {
                    map.put("client", tempClient);
                    map.put("contentId", x.getId());
                    map.put("content", x.getContent());
                    result.add(map);
                }
            }
            iter.getAndIncrement();
        });
        return Response.success("搜索成功", result);
    }

    @Override
    public Response deleteComment(Integer sheetId, Integer clientId) {
        QueryWrapper<SheetComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sheet_id", sheetId).eq("client_id", clientId);
        if (sheetCommentMapper.delete(queryWrapper) > 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response deleteOneComment(Integer id) {
        if (sheetCommentMapper.deleteById(id) > 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response addComment(SheetComment comment) {
        comment.setCreateTime(new Date(System.currentTimeMillis()));
        comment.setUpdateTime(new Date(System.currentTimeMillis()));
        if (sheetCommentMapper.insert(comment) > 0)
            return Response.success("添加成功");
        return Response.error("添加失败");
    }
}
