package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.MyListContain;
import com.example.music_player.entity.User;
import com.example.music_player.mapper.MyListContainMapper;
import com.example.music_player.mapper.UserMapper;
import com.example.music_player.service.MyListContainService;
import com.example.music_player.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MyListContainServiceImpl
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:12
 * @Version 1.0
 **/
@Service
public class MyListContainServiceImpl extends ServiceImpl<MyListContainMapper, MyListContain> implements MyListContainService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyListContainMapper myListContainMapper;

    @Autowired
    private SongService songService;

    /**
     * @Author Abel
     * @Description 获取用户播放列表
     * @Date 0:32 2022/10/18
     **/
    @Override
    public Response getPlaylistOrLoveByCid(Integer cid, Integer type) {
        Map<String, Object> map = new HashMap<>();
        User client = userMapper.selectById(cid);

        if (!client.getAuthority().equals("client"))
            return Response.fatal("获取失败");

        map.put("client", client);

        List<Integer> list = myListContainMapper.getPlaylistOrLoveByCid(cid, type);
        if (list == null) {
            map.put("songList", null);
        } else {
            List<Map<String, Object>> songList = new ArrayList<>();
            for (Integer sid : list) {
                songList.add((Map<String, Object>)songService.getSongSinger(sid).getData());
            }
            map.put("songList", songList);
        }
        return Response.success("获取成功", map);
    }

    @Override
    public Response getPlaylistOrLoveByCidPage(Integer numOfPage, Integer pageNo, Integer cid, Integer type) {
        Map<String, Object> map = new HashMap<>();
        User client = userMapper.selectById(cid);

        if (!client.getAuthority().equals("client"))
            return Response.fatal("获取失败");

        map.put("client", client);

        List<Integer> list = myListContainMapper.getPlaylistOrLoveByCid(cid, type);
        if (list == null) {
            map.put("songList", null);
        } else {
            List<Map<String, Object>> songList = new ArrayList<>();
            int iter = 1;
            for (Integer sid : list) {
                if (iter > numOfPage * (pageNo - 1) && iter <= numOfPage * pageNo) {
                    songList.add((Map<String, Object>) songService.getSongSinger(sid).getData());
                }
                iter++;
            }
            map.put("songList", songList);
        }
        return Response.success("获取成功", map);
    }

    @Override
    public Response delPlaylistOrLoveBySid(Integer cid, Integer sid, Integer type) {
        QueryWrapper<MyListContain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", cid).eq("song_id", sid);
        MyListContain myListContain = myListContainMapper.selectOne(queryWrapper);
        if (!myListContain.getType().equals(type)) {
            myListContain.setType(type);
            if (myListContainMapper.updateById(myListContain) != 0)
                return Response.success("删除成功");
        } else {
            if (myListContainMapper.delete(queryWrapper) != 0)
                return Response.success("删除成功");
        }
        return Response.error("删除失败");
    }

    @Override
    public Response delPlayListByCid(Integer cid) {
        QueryWrapper<MyListContain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", cid).eq("type", 0).or().eq("type", 2);
        if (!myListContainMapper.exists(queryWrapper)) return Response.success("删除成功");

        queryWrapper.clear();//功能不晓得
        queryWrapper.eq("type", 2).eq("client_id", cid);
        UpdateWrapper<MyListContain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("type", 1).eq("type", 0);
        if (myListContainMapper.delete(queryWrapper) != 0 && myListContainMapper.update(null, updateWrapper) != 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response addSongToPlaylist(MyListContain myListContain) {
        QueryWrapper<MyListContain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", myListContain.getClientId()).eq("song_id", myListContain.getSongId());

        if (myListContainMapper.exists(queryWrapper)) {
            myListContain.setType(0);
            if (myListContainMapper.updateById(myListContain) != 0)
                return Response.success("添加成功");
        } else {
            if (myListContainMapper.insert(myListContain) != 0)
                return Response.success("添加成功");
        }
        return Response.error("删除失败");
    }

    @Override
    public Response searchPlaylistOrLoveByCid(Integer cid, Integer type, String name) {
        Map<String, Object> map = new HashMap<>();
        User client = userMapper.selectById(cid);

        if (!client.getAuthority().equals("client"))
            return Response.fatal("获取失败");

        map.put("client", client);

        List<Integer> list = myListContainMapper.getPlaylistOrLoveByCid(cid, type);
        if (list == null) {
            map.put("songList", null);
        } else {
            List<Map<String, Object>> songList = new ArrayList<>();
            for (Integer sid : list) {
                Response temp = songService.getSongSingerLikeName(sid, name);
                if (temp != null)
                    songList.add((Map<String, Object>) temp.getData());
            }
            map.put("songList", songList);
        }
        return Response.success("获取成功", map);
    }

    @Override
    public Response searchPlaylistOrLoveByCidPage(Integer numOfPage, Integer pageNo, Integer cid, Integer type, String name) {
        Map<String, Object> map = new HashMap<>();
        User client = userMapper.selectById(cid);

        if (!client.getAuthority().equals("client"))
            return Response.fatal("获取失败");

        map.put("client", client);

        List<Integer> list = myListContainMapper.getPlaylistOrLoveByCid(cid, type);
        if (list == null) {
            map.put("songList", null);
        } else {
            List<Map<String, Object>> songList = new ArrayList<>();
            int iter = 1;
            for (Integer sid : list) {
                Response temp = songService.getSongSingerLikeName(sid, name);
                if (temp != null) {
                    if (iter > numOfPage * (pageNo - 1) && iter <= numOfPage * pageNo)
                        songList.add((Map<String, Object>) temp.getData());
                    iter++;
                }
            }
            map.put("songList", songList);
        }
        return Response.success("获取成功", map);
    }
}
