package com.example.music_player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.music_player.entity.Song;
import org.springframework.stereotype.Repository;

/**
 * @ClassName SongMapper
 * @Description
 * @Author Abel
 * @Date 2022/10/16 20:53
 * @Version 1.0
 **/
@Repository
public interface SongMapper extends BaseMapper<Song> {
}
