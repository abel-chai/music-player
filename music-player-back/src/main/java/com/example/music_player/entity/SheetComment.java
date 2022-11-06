package com.example.music_player.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName SheetComment
 * @Description 歌单评论
 * @Author Abel
 * @Date 2022/10/16 20:25
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SheetComment {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer clientId;

    private Integer sheetId;

    private String content;

    private Integer stars;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
