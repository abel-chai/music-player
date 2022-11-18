package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.MySheet;
import com.example.music_player.entity.Sheet;
import com.example.music_player.entity.User;
import com.example.music_player.mapper.MySheetMapper;
import com.example.music_player.mapper.SheetMapper;
import com.example.music_player.mapper.SingerMapper;
import com.example.music_player.mapper.UserMapper;
import com.example.music_player.service.MySheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName SongListCollectedServiceImpl
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:20
 * @Version 1.0
 **/
@Service
public class MySheetServiceImpl extends ServiceImpl<MySheetMapper, MySheet> implements MySheetService {
    @Autowired
    private MySheetMapper mySheetMapper;
    @Autowired
    private UserMapper clientMapper;
    @Autowired
    private SheetMapper sheetMapper;

    @Override
    public Response getMySheetListByCid(Integer cid) {
        Map<String, Object> map = new HashMap<>();
        User client = clientMapper.selectById(cid);

        if (!client.getAuthority().equals("client"))
            return Response.fatal("获取失败");

        map.put("client", client);

        List<Integer> list = mySheetMapper.getMySheetListByCid(cid);
        if (list == null) {
            map.put("sheetList", null);
        } else {
            List<Sheet> sheetList = sheetMapper.selectBatchIds(list);
            map.put("sheetList", sheetList);
        }

        return Response.success("获取成功", map);
    }

    @Override
    public Response getMySheetListByCidPage(Integer numOfPage, Integer pageNo, Integer cid) {
        Map<String, Object> map = new HashMap<>();
        User client = clientMapper.selectById(cid);

        if (!client.getAuthority().equals("client"))
            return Response.fatal("获取失败");

        map.put("client", client);

        List<Integer> list = mySheetMapper.getMySheetListByCid(cid);
        if (list == null) {
            map.put("sheetList", null);
        } else {
            List<Integer> subList = new ArrayList<>();
            AtomicInteger iter = new AtomicInteger(1);
            list.forEach((x) -> {
                if (iter.get() > numOfPage * (pageNo - 1) && iter.get() <= numOfPage * pageNo) {
                    subList.add(x);
                }
                iter.getAndIncrement();
            });
            List<Sheet> sheetList = sheetMapper.selectBatchIds(subList);
            map.put("sheetList", sheetList);
        }

        return Response.success("获取成功", map);
    }

    @Override
    public Response addSheetBySid(Integer cid, Sheet sheet) {
        sheet.setImg("/img/sheetImg/default.png");
        sheetMapper.insert(sheet);
        MySheet mySheet = new MySheet();
        mySheet.setSheetId(sheet.getId());
        mySheet.setClientId(cid);
        mySheetMapper.insert(mySheet);
        return Response.success("添加成功");
    }

    @Override
    public Response delMySheetBySid(Integer sid) {
        if (sheetMapper.deleteById(sid) != 0)
            return Response.success("删除成功");
        return Response.error("删除失败");
    }
}
