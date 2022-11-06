package com.example.music_player.config;

import com.example.music_player.constants.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:Gary
 * @ProjectName:music_player_test
 * @Date: 2022/10/25 17:32
 **/
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    // 这个配置类的目的是什么  就是注册了一个类似于拦截器吧  看到对应的资源 会将其修改成相应的地址
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/clientImg/**")
                .addResourceLocations(Constants.CLIENT_IMG_PATH);
        registry.addResourceHandler("/img/singerImg/**")
                .addResourceLocations(Constants.SINGER_IMG_PATH);
        registry.addResourceHandler("/img/songImg/**")
                .addResourceLocations(Constants.SONG_IMG_PATH);
        registry.addResourceHandler("/music/**")
                .addResourceLocations(Constants.MUSIC_PATH);
        registry.addResourceHandler("/img/sheetImg/**")
                .addResourceLocations(Constants.SHEET_IMG_PATH);
        registry.addResourceHandler("/img/bannerImg/**")
                .addResourceLocations(Constants.BANNER_IMG_PATH);
    }

}
