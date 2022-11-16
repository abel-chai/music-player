package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.Sheet;
import com.example.music_player.mapper.SheetMapper;
import com.example.music_player.service.SheetService;
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
 * @ClassName SongListServcieImpl
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:23
 * @Version 1.0
 **/
@Service
public class SheetServiceImpl extends ServiceImpl<SheetMapper, Sheet> implements SheetService {
    @Autowired
    SheetMapper sheetMapper;

    @Override
    public Response updateSheetInfo(Sheet sheet) {
        if (sheetMapper.updateById(sheet) > 0)
            return Response.success("更新成功");
        return Response.error("更新失败");
    }

    @Override
    public Response updateSheetAvatar(MultipartFile avatarFile, int id) {
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "sheetImg";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/sheetImg/" + fileName;
        try {
            avatarFile.transferTo(dest);
        } catch (IOException e) {
            return Response.fatal("上传失败" + e.getMessage());
        }
        Sheet sheet = new Sheet();
        sheet.setId(id);
        sheet.setImg(imgPath);
        if (sheetMapper.updateById(sheet) > 0) {
            return Response.success("上传成功", imgPath);
        } else {
            return Response.error("上传失败");
        }
    }

    @Override
    public Response searchSheetByName(String name) {
        QueryWrapper<Sheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", name);

        return Response.success("获取成功", sheetMapper.selectList(queryWrapper));
    }

    @Override
    public Response searchSheetByNamePage(Integer numOfPage, Integer pageNo, String title) {
        Page<Sheet> sheetPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<Sheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        sheetMapper.selectPage(sheetPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("sheetList", sheetPage.getRecords());
        map.put("totalNum", sheetPage.getTotal());
        map.put("pageNum", sheetPage.getPages());
        map.put("hasPrevious", sheetPage.hasPrevious());
        map.put("hasNext", sheetPage.hasNext());
        return Response.success("获取成功", map);
    }

    @Override
    public Response getSheetByStyle(String style) {
        QueryWrapper<Sheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!"all".equals(style), "style", style).isNotNull("style");
        return Response.success("获取成功", sheetMapper.selectList(queryWrapper));
    }

    @Override
    public Response getSheetByStylePage(Integer numOfPage, Integer pageNo, String style) {
        Page<Sheet> sheetPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<Sheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!"all".equals(style), "style", style).isNotNull("style");
        sheetMapper.selectPage(sheetPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("sheetList", sheetPage.getRecords());
        map.put("totalNum", sheetPage.getTotal());
        map.put("pageNum", sheetPage.getPages());
        map.put("hasPrevious", sheetPage.hasPrevious());
        map.put("hasNext", sheetPage.hasNext());
        return Response.success("获取成功", map);
    }

    @Override
    public Response addSheet(Sheet sheet) {
        if (sheetMapper.selectCount(new QueryWrapper<Sheet>().eq("title", sheet.getTitle())) > 0)
            return Response.error("添加失败");
        sheet.setImg("/img/sheetImg/default.png");
        if (sheetMapper.insert(sheet) > 0)
            return Response.success("添加成功");
        return Response.error("添加失败");
    }

    @Override
    public Response searchSheet(String title) {
        QueryWrapper<Sheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        return Response.success("查询成功", sheetMapper.selectList(queryWrapper));
    }

    @Override
    public Response searchSheet(Integer numOfPage, Integer pageNo, String title) {
        Page<Sheet> sheetPage = new Page<>(pageNo, numOfPage);
        QueryWrapper<Sheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        sheetMapper.selectPage(sheetPage, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("sheetList", sheetPage.getRecords());
        map.put("totalNum", sheetPage.getTotal());
        map.put("pageNum", sheetPage.getPages());
        map.put("hasPrevious", sheetPage.hasPrevious());
        map.put("hasNext", sheetPage.hasNext());
        return Response.success("获取成功", map);
    }

    @Override
    public Response deleteSheet(int id) {
        if (sheetMapper.deleteById(id) > 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }

    @Override
    public Response getSheetAllStyleNum() {
        List<Map<String, Object>> result = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        QueryWrapper<Sheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("style", "华语");
        map1.put("name", "华语");
        map1.put("value", sheetMapper.selectCount(queryWrapper));
        result.add(map1);
        queryWrapper.clear();

        Map<String, Object> map2 = new HashMap<>();
        queryWrapper.like("style", "粤语");
        map2.put("name", "粤语");
        map2.put("value", sheetMapper.selectCount(queryWrapper));
        result.add(map2);
        queryWrapper.clear();

        Map<String, Object> map3 = new HashMap<>();
        queryWrapper.like("style", "欧美");
        map3.put("name", "欧美");
        map3.put("value", sheetMapper.selectCount(queryWrapper));
        result.add(map3);
        queryWrapper.clear();

        Map<String, Object> map4 = new HashMap<>();
        queryWrapper.like("style", "日韩");
        map4.put("name", "日韩");
        map4.put("value", sheetMapper.selectCount(queryWrapper));
        result.add(map4);
        queryWrapper.clear();

        Map<String, Object> map5 = new HashMap<>();
        queryWrapper.like("style", "BGM");
        map5.put("name", "BGM");
        map5.put("value", sheetMapper.selectCount(queryWrapper));
        result.add(map5);
        queryWrapper.clear();

        Map<String, Object> map6 = new HashMap<>();
        queryWrapper.like("style", "轻音乐");
        map6.put("name", "轻音乐");
        map6.put("value", sheetMapper.selectCount(queryWrapper));
        result.add(map6);
        queryWrapper.clear();

        Map<String, Object> map7 = new HashMap<>();
        queryWrapper.like("style", "乐器");
        map7.put("name", "乐器");
        map7.put("value", sheetMapper.selectCount(queryWrapper));
        result.add(map7);

        return Response.success("获取成功", result);
    }

    @Override
    public Response getAllSheetPage(Integer numOfPage, Integer pageNo) {
        Page<Sheet> sheetPage = new Page<>(pageNo, numOfPage);
        sheetMapper.selectPage(sheetPage, null);

        Map<String, Object> map = new HashMap<>();
        map.put("sheetList", sheetPage.getRecords());
        map.put("totalNum", sheetPage.getTotal());
        map.put("pageNum", sheetPage.getPages());
        map.put("hasPrevious", sheetPage.hasPrevious());
        map.put("hasNext", sheetPage.hasNext());
        return Response.success("获取成功", map);
    }


}
