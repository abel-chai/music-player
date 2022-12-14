package com.example.music_player.config;

import com.example.music_player.filter.JwtAuthenticationTokenFilter;
import com.example.music_player.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author:Gary
 * @ProjectName:music_player
 * @Date: 2022/10/22 16:36
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication()
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * ??????:
     * http????????? Spring Security ?????????????????????????????????????????????????????????web??????????????? Spring Security ???????????????
     * ??????http???????????????????????????????????????web??????????????????????????????
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //??????csrf
                .csrf().disable()
                //?????????Session??????SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // ????????????????????????????????????TOKEN
                .antMatchers("/client/register").permitAll()
                // swagger
                .antMatchers("/swagger-resources/**", "/v3/**"
                        , "/swagger-ui/**").permitAll()
                // client????????????
                .antMatchers(HttpMethod.GET, "/song/**", "/banner/**"
                        , "/sheet/**", "/singer/**", "/sheetContain/pre/**", "/sheetComment/**").permitAll()
                // ????????????????????????
                .antMatchers("/img/clientImg/**",
                        "/img/singerImg/**",
                        "/img/songImg/**",
                        "/img/sheetImg/**",
                        "/img/bannerImg/**",
                        "/music/**").permitAll()
                // ?????????????????? ??????????????????
                .antMatchers("/auth/login").anonymous()
                // ???????????????????????????????????????????????????
                .anyRequest().authenticated();

        //???token???????????????????????????????????????
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

        //????????????
        http.cors();
    }

    /**
     * ???????????????????????????????????????????????????????????????
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // ??????????????? ?????????????????????
        provider.setHideUserNotFoundExceptions(true);
        // ????????????service - ????????????????????????
        provider.setUserDetailsService(userDetailsService());
        // ????????????????????????
        provider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(provider);
    }

    /**
     * ??????: ??????????????????UserDetailsService ????????????????????????????????????
     **/
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    /**
     * ??????: ?????????????????? BCrypt ????????????
     **/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * ??????: ??????AuthenticationManager?????????
     **/
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}

