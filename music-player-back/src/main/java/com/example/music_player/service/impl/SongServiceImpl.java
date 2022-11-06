package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.Singer;
import com.example.music_player.entity.Song;
import com.example.music_player.mapper.SingerMapper;
import com.example.music_player.mapper.SongMapper;
import com.example.music_player.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SongServiceImpl
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:29
 * @Version 1.0
 **/
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public Response updateSongAvatar(MultipartFile avatarFile, int id) {
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songImg";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                return Response.fatal("创建文件夹失败");
            }
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/img/songImg/" + fileName;
        try {
            avatarFile.transferTo(dest);
        } catch (IOException e) {
            return Response.fatal("上传失败" + e.getMessage());
        }
        Song song = new Song();
        song.setId(id);
        song.setImg(storeUrlPath);
        if (songMapper.updateById(song) > 0) {
            return Response.success("上传成功", storeUrlPath);
        } else {
            return Response.error("上传失败");
        }
    }

    @Override
    public Response updateSongSource(MultipartFile musicFile, int id) {
        String fileName = musicFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "music";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                return Response.fatal("创建目的文件夹失败");
            }
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/music/" + fileName;
        try {
            musicFile.transferTo(dest);
        } catch (IOException e) {
            return Response.fatal("更新失败" + e.getMessage());
        }
        Song song = new Song();
        song.setId(id);
        song.setUrl(storeUrlPath);
        if (songMapper.updateById(song) > 0) {
            return Response.success("更新成功", storeUrlPath);
        } else {
            return Response.error("更新失败");
        }
    }

    @Override
    public Response updateSongInfo(Song song) {
        if (songMapper.updateById(song) > 0)
            return Response.success("更新成功");
        return Response.error("更新失败");
    }

    @Override
    public Response getSongBySingerId(Integer singerId) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id", singerId);
        return Response.success("搜索成功", songMapper.selectList(queryWrapper));
    }

    @Override
    public Response getSongBySingerIdPage(Integer numOfPage, Integer pageNo, Integer singerId) {
        Page<Song> songPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id", singerId);
        songMapper.selectPage(songPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("songList", songPage.getRecords());
        map.put("totalNum", songPage.getTotal());
        map.put("pageNum", songPage.getPages());
        map.put("hasPrevious", songPage.hasPrevious());
        map.put("hasNext", songPage.hasNext());
        return Response.success("搜索成功", map);
    }

    @Override
    public Response getSong(Integer singerId, String name) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id", singerId).like("song_name", name);
        return Response.success("查询成功", songMapper.selectList(queryWrapper));
    }

    @Override
    public Response getSongPage(Integer numOfPage, Integer pageNo, Integer singerId, String name) {
        Page<Song> songPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id", singerId).like("song_name", name);
        songMapper.selectPage(songPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("songList", songPage.getRecords());
        map.put("totalNum", songPage.getTotal());
        map.put("pageNum", songPage.getPages());
        map.put("hasPrevious", songPage.hasPrevious());
        map.put("hasNext", songPage.hasNext());
        return Response.success("查询成功", map);
    }

    @Override
    public Response addSong(MultipartFile musicFile, Song song) {
        String pic = "/img/songPic/tubiao.jpg";
        String fileName = musicFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "music";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                return Response.fatal("创建文件失败");
            }
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/music/" + fileName;
        try {
            musicFile.transferTo(dest);
        } catch (IOException e) {
            return Response.fatal("上传失败" + e.getMessage());
        }
        song.setImg(pic);
        song.setUrl(storeUrlPath);
        if (songMapper.insert(song) > 0) {
            return Response.success("上传成功", storeUrlPath);
        } else {
            return Response.error("上传失败");
        }
    }

    @Override
    public Response getSongSinger(Integer sid) {
        Map<String, Object> map = new HashMap<>();
        Song song = songMapper.selectById(sid);
        Singer singer = singerMapper.selectById(song.getSingerId());

        map.put("song", song);
        map.put("singer", singer);

        return Response.success("获取成功", map);
    }

    @Override
    public Response searchSong(String name) {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.like("singer_name", name);
        List<Singer> singerList = singerMapper.selectList(singerQueryWrapper);

        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.like("song_name", name);

        if (!singerList.isEmpty()) {
            for (Singer singer : singerList) {
                songQueryWrapper.or().eq("singer_id", singer.getId());
            }
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (Song song : songMapper.selectList(songQueryWrapper)) {
            list.add((Map<String, Object>) getSongSinger(song.getId()).getData());
        }

        return Response.success("查询成功", list);
    }

    @Override
    public Response getSongById(Integer id) {
        return Response.success("获取成功", songMapper.selectById(id));
    }

    @Override
    public Response deleteSong(int id) {
        if (songMapper.deleteById(id) > 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response searchSongPage(Integer numOfPage, Integer pageNo, String name) {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.like("singer_name", name);
        List<Singer> singerList = singerMapper.selectList(singerQueryWrapper);

        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.like("song_name", name);

        if (!singerList.isEmpty()) {
            for (Singer singer : singerList) {
                songQueryWrapper.or().eq("singer_id", singer.getId());
            }
        }

        List<Map<String, Object>> list = new ArrayList<>();
        int iter = 1;
        for (Song song : songMapper.selectList(songQueryWrapper)) {
            if (iter > numOfPage * (pageNo - 1) && iter <= numOfPage * pageNo) {
                list.add((Map<String, Object>) getSongSinger(song.getId()).getData());
            }
            iter++;
        }

        return Response.success("查询成功", list);
    }

    @Override
    public Response getSongSingerLikeName(Integer sid, String name) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", sid).like("song_name", name);
        Song song = songMapper.selectOne(queryWrapper);
        if (song == null)
            return null;
        Singer singer = singerMapper.selectById(song.getSingerId());

        map.put("song", song);
        map.put("singer", singer);

        return Response.success("获取成功", map);
    }
}
