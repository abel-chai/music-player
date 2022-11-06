package com.example.music_player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SongListMark
 * @Description 歌单评分
 * @Author Abel
 * @Date 2022/10/16 20:28
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SheetMark {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer clientId;

    private Integer sheetId;

    private String score;
}
