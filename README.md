# music-player
大三上软件工程项目 - 在线音乐播放器

## 项目介绍
 - 基于springboot + vue的前后端分离项目;主要分为后端以及前端用户界面和后台管理界面;使用git和github完成版本控制
 - 后端负责借助数据库mysql以及redis完成对数据的存储，通过逻辑处理完成对前端请求的响应
 - 前台用户页面可以实现本系统所有歌单，歌手等的显示，用户可以通过点击完成歌曲的播放，同时可以对歌单进行评价等
 - 后台管理页面可以查看本系统目前用户、歌手、歌和歌单的详细数据，同时可以新增或修改歌曲，歌单，歌手信息等
 
## 技术栈
### 后端
 - 使用springboot作为主体框架
 - 使用mybatis-plus作为连接数据库的框架
 - 使用springsecurity实现用户授权和用户访问控制
 - 使用swagger自动生成接文档
 - 使用redis作为前台页面与后端服务器之间的缓存服务器
### 前台用户页面                                       
 - 使用vue3作为主体框架
### 后台管理页面                                               
 - 使用vue2作为主体框架
 - 使用element ui组件库简化页面编写
 - 使用vuex实现组件间通信
 - 使用router实现组件间路由
 - 使用axios实现前后端通信
 - 使用echarts完成饼状图，柱状图的绘制
 
## 开发环境
JDK：jdk 8 \
mysql: mysql 8 \
redis: 4.0.9 $\quad$ 基于ubuntu服务器环境 \
node: v12.20.0 \
IDE: IntelliJ IDEA2022.1 $\quad$ vscode $\quad$ Hyper-V $\quad$ Vmware $\quad$ putty

## 使用说明
1. 使用git下载项目到本地
```
git@github.com:abel-chai/music-player.git
```
2. 完成数据库及表的建立并导入相关数据
3. 修改配置文件
将src/main/resources/application.yaml文件中的数据库配置项和redis配置项改为本地配置
4. 运行项目 
 - 开启虚拟机或本地的redis
 - 运行后端服务器
 - 运行前台用户页面
 ```
 npm i //安装依赖
 npm run dev //运行服务器
 ```
 - 运行后台管理页面
 ```
 npm i //安装依赖
 npm run dev //运行服务器
 ```
 
 ## LICENSE
[Apache-2.0 license](https://github.com/abel-chai/music-player/blob/main/LICENSE)
