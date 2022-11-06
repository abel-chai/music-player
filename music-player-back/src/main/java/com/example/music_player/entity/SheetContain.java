package com.example.music_player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SheetContain
 * @Description 歌单与歌
 * @Author Abel
 * @Date 2022/10/16 20:19
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SheetContain {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer sheetId;

    private Integer songId;
}
