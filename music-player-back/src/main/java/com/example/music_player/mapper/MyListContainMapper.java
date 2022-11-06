package com.example.music_player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.music_player.entity.MyListContain;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName MyListContainMapper
 * @Description
 * @Author Abel
 * @Date 2022/10/16 20:52
 * @Version 1.0
 **/
@Repository
public interface MyListContainMapper extends BaseMapper<MyListContain> {
    List<Integer> getPlaylistOrLoveByCid(Integer cid, Integer type);
}
