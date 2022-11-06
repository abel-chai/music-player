package com.example.music_player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author:Gary
 * @ProjectName:music_player_test
 * @Date: 2022/10/25 15:53
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String sex;

    private Date birthday;

    private String signature;

    private String location;

    private String phone;

    private String email;

    private String img;

    private String authority;

    @TableLogic
    private int isDelete;
}
