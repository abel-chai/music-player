package com.example.music_player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.music_player.entity.SheetContain;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName SongListContainsMapper
 * @Description
 * @Author Abel
 * @Date 2022/10/16 20:56
 * @Version 1.0
 **/
@Repository
public interface SheetContainMapper extends BaseMapper<SheetContain> {
    List<Integer> getSheetContainBySid(Integer sid);
}
