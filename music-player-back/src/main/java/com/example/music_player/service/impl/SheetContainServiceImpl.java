package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.Sheet;
import com.example.music_player.entity.SheetContain;
import com.example.music_player.entity.Singer;
import com.example.music_player.entity.Song;
import com.example.music_player.mapper.SheetContainMapper;
import com.example.music_player.mapper.SheetMapper;
import com.example.music_player.mapper.SingerMapper;
import com.example.music_player.mapper.SongMapper;
import com.example.music_player.service.SheetContainService;
import com.example.music_player.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName SongListContainServiceImpl
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:16
 * @Version 1.0
 **/
@Service
public class SheetContainServiceImpl extends ServiceImpl<SheetContainMapper, SheetContain> implements SheetContainService {
    @Autowired
    SheetContainMapper sheetContainMapper;

    @Autowired
    SongMapper songMapper;

    @Autowired
    SingerMapper singerMapper;

    @Autowired
    SheetMapper sheetMapper;

    @Autowired
    private SongService songService;

    @Override
    public Response getSongBySheet(Integer sheetId) {
        QueryWrapper<SheetContain> sheetContainWrapper = new QueryWrapper<>();
        sheetContainWrapper.eq("sheet_id", sheetId).select("song_id");
        List<Object> songIdList = sheetContainMapper.selectObjs(sheetContainWrapper);
        List<Song> songList = new ArrayList<>();
        songIdList.forEach((x) -> {
            Song temp = songMapper.selectById(Integer.parseInt(x.toString()));
            if (temp != null)
                songList.add(temp);
        });
        return Response.success("获取成功", songList);
    }

    @Override
    public Response getSongBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId) {
        QueryWrapper<SheetContain> sheetContainWrapper = new QueryWrapper<>();
        sheetContainWrapper.eq("sheet_id", sheetId).select("song_id");
        List<Object> songIdList = sheetContainMapper.selectObjs(sheetContainWrapper);
        List<Song> songList = new ArrayList<>();
        AtomicInteger iter = new AtomicInteger(1);
        songIdList.forEach((x) -> {
            Song temp = songMapper.selectById(Integer.parseInt(x.toString()));
            if (temp != null && iter.get() > numOfPage * (pageNo - 1) && iter.get() <= numOfPage * pageNo) {
                songList.add(temp);
            }
            iter.getAndIncrement();
        });
        return Response.success("获取成功", songList);
    }

    @Override
    public Response searchSongBySheet(Integer sheetId, String name) {
        QueryWrapper<SheetContain> sheetContainWrapper = new QueryWrapper<>();
        sheetContainWrapper.eq("sheet_id", sheetId).select("song_id");
        List<Object> songIdList = sheetContainMapper.selectObjs(sheetContainWrapper);
        QueryWrapper<Song> songWrapper = new QueryWrapper<>();
        List<Song> songList = new ArrayList<>();
        songIdList.forEach((x) -> {
            songWrapper.clear();
            songWrapper.like("song_name", name).eq("id", Integer.parseInt(x.toString()));
            Song temp = songMapper.selectOne(songWrapper);
            if (temp != null)
                songList.add(temp);
        });
        return Response.success("查询成功", songList);
    }

    @Override
    public Response searchSongBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId, String name) {
        QueryWrapper<SheetContain> sheetContainWrapper = new QueryWrapper<>();
        sheetContainWrapper.eq("sheet_id", sheetId).select("song_id");
        List<Object> songIdList = sheetContainMapper.selectObjs(sheetContainWrapper);
        QueryWrapper<Song> songWrapper = new QueryWrapper<>();
        List<Song> songList = new ArrayList<>();
        AtomicInteger iter = new AtomicInteger(1);
        songIdList.forEach((x) -> {
            songWrapper.clear();
            songWrapper.like("song_name", name).eq("id", Integer.parseInt(x.toString()));
            Song temp = songMapper.selectOne(songWrapper);
            if (temp != null && iter.get() > numOfPage * (pageNo - 1) && iter.get() <= numOfPage * pageNo) {
                songList.add(temp);
            }
            iter.getAndIncrement();
        });
        return Response.success("查询成功", songList);
    }

    @Override
    public Response deleteSongInSheet(Integer sheetId, Integer songId) {
        QueryWrapper<SheetContain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sheet_id", sheetId).eq("song_id", songId);
        if (sheetContainMapper.delete(queryWrapper) > 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response addSongInSheet(Integer sheetId, String singerName, String songName) {
        QueryWrapper<Singer> singerWrapper = new QueryWrapper<>();
        singerWrapper.eq("singer_name", singerName).select("id");
        Singer singer = singerMapper.selectOne(singerWrapper);
        QueryWrapper<Song> songWrapper = new QueryWrapper<>();
        songWrapper.eq("song_name", songName).eq("singer_id", singer.getId()).select("id");
        Song song = songMapper.selectOne(songWrapper);
        if (sheetContainMapper.selectCount((new QueryWrapper<SheetContain>()
                .eq("sheet_id", sheetId)
                .eq("song_id", song.getId()))) > 0)
            return Response.error("添加失败");
        if (sheetContainMapper.insert(new SheetContain(null, sheetId, song.getId())) >= 0)
            return Response.success("添加成功");
        return Response.error("添加失败");
    }

    @Override
    public Response getSheetContainBySid(Integer sid) {
        Map<String, Object> map = new HashMap<>();
        Sheet sheet = sheetMapper.selectById(sid);
        map.put("sheet", sheet);

        List<Integer> list = sheetContainMapper.getSheetContainBySid(sid);
        if (list == null) {
            map.put("songList", null);
        } else {
            List<Map<String, Object>> songList = new ArrayList<>();
            for (Integer songId : list) {
                songList.add((Map<String, Object>) songService.getSongSinger(songId).getData());
            }
            map.put("songList", songList);
        }
        return Response.success("获取成功", map);
    }

    @Override
    public Response getSheetContainBySidPage(Integer numOfPage, Integer pageNo, Integer sid) {
        Map<String, Object> map = new HashMap<>();
        Sheet sheet = sheetMapper.selectById(sid);
        map.put("sheet", sheet);

        List<Integer> list = sheetContainMapper.getSheetContainBySid(sid);
        if (list == null) {
            map.put("songList", null);
        } else {
            List<Map<String, Object>> songList = new ArrayList<>();
            int iter = 1;
            for (Integer songId : list) {
                if (iter > numOfPage * (pageNo - 1) && iter <= numOfPage * pageNo) {
                    songList.add((Map<String, Object>) songService.getSongSinger(songId).getData());
                }
                iter++;
            }
            map.put("songList", songList);
        }
        return Response.success("获取成功", map);
    }

    @Override
    public Response deleteOneSongInSheet(Integer id) {
        if (sheetContainMapper.deleteById(id) > 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response searchSongSingerBySheet(Integer sheetId, String name) {
        QueryWrapper<SheetContain> sheetContainWrapper = new QueryWrapper<>();
        sheetContainWrapper.eq("sheet_id", sheetId).select("song_id");
        List<Object> songIdList = sheetContainMapper.selectObjs(sheetContainWrapper);
        QueryWrapper<Song> songWrapper = new QueryWrapper<>();
        List<Map<String, Object>> songList = new ArrayList<>();
        songIdList.forEach((x) -> {
            songWrapper.clear();
            songWrapper.like("song_name", name).eq("id", Integer.parseInt(x.toString()));
            Song tempSong = songMapper.selectOne(songWrapper);
            if (tempSong != null) {
                Singer tempSinger = singerMapper.selectById(tempSong.getSingerId());
                Map<String, Object> map = new HashMap<>();
                map.put("song", tempSong);
                map.put("singer", tempSinger);
                songList.add(map);
            }
        });
        return Response.success("查询成功", songList);
    }

    @Override
    public Response searchSongSingerBySheetPage(Integer numOfPage, Integer pageNo, Integer sheetId, String name) {
        QueryWrapper<SheetContain> sheetContainWrapper = new QueryWrapper<>();
        sheetContainWrapper.eq("sheet_id", sheetId).select("song_id");
        List<Object> songIdList = sheetContainMapper.selectObjs(sheetContainWrapper);
        QueryWrapper<Song> songWrapper = new QueryWrapper<>();
        List<Map<String, Object>> songList = new ArrayList<>();
        AtomicInteger iter = new AtomicInteger(1);
        songIdList.forEach((x) -> {
            songWrapper.clear();
            songWrapper.like("song_name", name).eq("id", Integer.parseInt(x.toString()));
            Song tempSong = songMapper.selectOne(songWrapper);
            if (tempSong != null) {
                Singer tempSinger = singerMapper.selectById(tempSong.getSingerId());
                Map<String, Object> map = new HashMap<>();
                map.put("song", tempSong);
                map.put("singer", tempSinger);
                if (iter.get() > numOfPage * (pageNo - 1) && iter.get() <= numOfPage * pageNo) {
                    songList.add(map);
                }
                iter.getAndIncrement();
            }
        });
        return Response.success("查询成功", songList);
    }
}
