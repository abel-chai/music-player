package com.example.music_player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.music_player.entity.MySheet;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName SongListCollectedMapper
 * @Description
 * @Author Abel
 * @Date 2022/10/16 20:54
 * @Version 1.0
 **/
@Repository
public interface MySheetMapper extends BaseMapper<MySheet> {
    List<Integer> getMySheetListByCid(Integer cid);
}
