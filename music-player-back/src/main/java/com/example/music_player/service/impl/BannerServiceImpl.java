package com.example.music_player.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music_player.common.Response;
import com.example.music_player.entity.Banner;
import com.example.music_player.mapper.BannerMapper;
import com.example.music_player.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/22 12:12
 **/
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Cacheable(value = "banner", key = "'list'")  //放在缓存中 redis 是以key-value进行存储的
    @Override
    public Response getAllBanner() {
        System.out.println("没有走缓存");
        return Response.success("获取成功", bannerMapper.selectList(null));
    }
}
