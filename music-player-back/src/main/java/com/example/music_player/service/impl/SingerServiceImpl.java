package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.Singer;
import com.example.music_player.mapper.SingerMapper;
import com.example.music_player.service.SingerService;
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
 * @ClassName SingerServiceImpl
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:13
 * @Version 1.0
 **/
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {
    @Autowired
    SingerMapper singerMapper;

    @Override
    public Response updateSingerAvatar(MultipartFile avatarFile, int id) {
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "singerImg";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/singerImg/" + fileName;
        try {
            avatarFile.transferTo(dest);
        } catch (IOException e) {
            return Response.fatal("上传失败" + e.getMessage());
        }
        Singer singer = new Singer();
        singer.setId(id);
        singer.setImg(imgPath);
        if (singerMapper.updateById(singer) > 0) {
            return Response.success("上传成功", imgPath);
        } else {
            return Response.error("上传失败");
        }
    }

    @Override
    public Response updateSingerInfo(Singer singer) {
        if (singerMapper.updateById(singer) > 0)
            return Response.success("更新成功");
        return Response.error("更新失败");
    }

    @Override
    public Response getSingerOneSexNum(String sex) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("sex", sex);
        return Response.success("获取成功", singerMapper.selectCount(queryWrapper));
    }

    @Override
    public Response getSingerAllSexNum() {
        Map<String, Integer> sexMap = new HashMap<>();
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("sex", "男");
        sexMap.put("man", Math.toIntExact(singerMapper.selectCount(queryWrapper)));
        queryWrapper.clear();
        queryWrapper.like("sex", "女");
        sexMap.put("woman", Math.toIntExact(singerMapper.selectCount(queryWrapper)));
        return Response.success("获取成功", sexMap);
    }

    @Override
    public Response getSingerCountryNum() {
        Map<String, List> countryMap = new HashMap<>();
        List<String> name = new ArrayList<>();
        List<Long> num = new ArrayList<>();
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("location", "中国");
        name.add("中国");
        num.add(singerMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.like("location", "韩国");
        name.add("韩国");
        num.add(singerMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.like("location", "意大利");
        name.add("意大利");
        num.add(singerMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.like("location", "新加坡");
        name.add("新加坡");
        num.add(singerMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.like("location", "美国");
        name.add("美国");
        num.add(singerMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.like("location", "西班牙");
        name.add("西班牙");
        num.add(singerMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.like("location", "日本");
        name.add("日本");
        num.add(singerMapper.selectCount(queryWrapper));

        countryMap.put("name", name);
        countryMap.put("value", num);

        return Response.success("获取成功", countryMap);
    }

    @Override
    public Response getSinger(String name) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("singer_name", name);
        return Response.success("搜索成功", singerMapper.selectList(queryWrapper));
    }

    @Override
    public Response getSingerPage(Integer numOfPage, Integer pageNo, String name) {
        Page<Singer> singerPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("singer_name", name);

        singerMapper.selectPage(singerPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("singerList", singerPage.getRecords());
        map.put("totalNum", singerPage.getTotal());
        map.put("pageNum", singerPage.getPages());
        map.put("hasPrevious", singerPage.hasPrevious());
        map.put("hasNext", singerPage.hasNext());
        return Response.success("搜索成功", map);
    }

    @Override
    public Response addSinger(Singer singer) {
        if (singerMapper.selectCount(new QueryWrapper<Singer>().eq("singer_name", singer.getSingerName())) > 0)
            return Response.error("添加失败");
        singer.setImg("/img/singerImg/default.png");
        if (singerMapper.insert(singer) > 0)
            return Response.success("添加成功");
        return Response.error("添加失败");
    }

    @Override
    public Response deleteSinger(int id) {
        if (singerMapper.deleteById(id) > 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response getAllSingerPage(Integer numOfPage, Integer pageNo) {
        Page<Singer> singerPage = new Page<>(pageNo, numOfPage);
        singerMapper.selectPage(singerPage, null);

        Map<String, Object> map = new HashMap<>();
        map.put("singerList", singerPage.getRecords());
        map.put("totalNum", singerPage.getTotal());
        map.put("pageNum", singerPage.getPages());
        map.put("hasPrevious", singerPage.hasPrevious());
        map.put("hasNext", singerPage.hasNext());
        return Response.success("搜索成功", map);
    }

    @Override
    public Response searchSingerSex(String sex) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex", sex);
        return Response.success("搜索成功", singerMapper.selectList(queryWrapper));
    }

    @Override
    public Response searchSingerCountry(String country) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("location", country);
        return Response.success("搜索成功", singerMapper.selectList(queryWrapper));
    }
}
