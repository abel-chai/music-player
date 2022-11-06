package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.SheetMark;
import com.example.music_player.mapper.SheetMarkMapper;
import com.example.music_player.service.SheetMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @ClassName SongListMarkServiceImpl
 * @Description
 * @Author Abel
 * @Date 2022/10/16 21:25
 * @Version 1.0
 **/
@Service
public class SheetMarkServiceImpl extends ServiceImpl<SheetMarkMapper, SheetMark> implements SheetMarkService {
    @Autowired
    SheetMarkMapper sheetMarkMapper;

    @Override
    public Response addMark(Integer cid, Integer sid, String score) {
        QueryWrapper<SheetMark> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", cid).eq("sheet_id", sid);
        if (sheetMarkMapper.selectCount(queryWrapper) > 0)
            return Response.error("已打分");
        Set<String> numSet = new HashSet<>();
        numSet.add("0");
        numSet.add("1");
        numSet.add("2");
        numSet.add("3");
        numSet.add("4");
        numSet.add("5");
        if (!numSet.contains(score))
            return Response.error("打分不合理");

        SheetMark sheetMark = new SheetMark();
        sheetMark.setSheetId(sid);
        sheetMark.setClientId(cid);
        sheetMark.setScore(score);
        if (sheetMarkMapper.insert(sheetMark) > 0)
            return Response.success("打分成功");
        return Response.error("打分失败");
    }

    @Override
    public Response getMark(Integer sid) {
        QueryWrapper<SheetMark> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sheet_id", sid);
        List<SheetMark> list = sheetMarkMapper.selectList(queryWrapper);
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        list.forEach((x) -> sum.updateAndGet(v -> v + Double.parseDouble(x.getScore())));
        Double avg = sum.get() / list.size();
        return Response.success("获取成功", avg);
    }
}
