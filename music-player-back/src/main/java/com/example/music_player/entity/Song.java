package com.example.music_player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Song
 * @Description 歌曲
 * @Author Abel
 * @Date 2022/10/16 20:14
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String songName;

    private String intro;

    private String lyric;

    private String img;

    private String url;

    private Integer singerId;

    @TableLogic
    private Integer isDelete;
}
