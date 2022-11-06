package com.example.music_player.common;

import lombok.Data;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/22 17:58
 **/
@Data
public class Response {

    private String message;

    private String type;

    private Object data;

    public static Response success(String message) {
        Response r = new Response();
        r.setMessage(message);
        r.setType("success");
        r.setData(null);
        return r;
    }

    public static Response success(String message, Object data) {
        Response r = success(message);
        r.setData(data);
        return r;
    }

    public static Response error(String message) {
        Response r = new Response();
        r.setMessage(message);
        r.setType("error");
        r.setData(null);
        return r;
    }

    public static Response fatal(String message) {
        Response r = error(message);
        return r;
    }
}
