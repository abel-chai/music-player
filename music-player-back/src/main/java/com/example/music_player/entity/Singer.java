package com.example.music_player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @ClassName singer
 * @Description 歌手
 * @Author Abel
 * @Date 2022/10/16 20:11
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Singer {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String singerName;

    private String sex;

    private Date birthday;

    private String location;

    private String intro;

    private String img;

    @TableLogic
    private Integer isDelete;
}
