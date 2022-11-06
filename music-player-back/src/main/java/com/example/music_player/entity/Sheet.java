package com.example.music_player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Sheet
 * @Description 歌单
 * @Author Abel
 * @Date 2022/10/16 20:16
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sheet {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String intro;

    private String style;

    private String img;

    @TableLogic
    private Integer isDelete;
}
