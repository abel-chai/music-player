package com.example.music_player.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/22 12:04
 **/
@Data
public class Banner implements Serializable {
    private Integer id;

    private String pic;

    private static final long serialVersionUID = 1L;

}
