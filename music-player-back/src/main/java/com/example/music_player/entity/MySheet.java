package com.example.music_player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName MySheet
 * @Description 歌单收藏
 * @Author Abel
 * @Date 2022/10/16 20:33
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySheet {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer clientId;

    private Integer sheetId;
}
