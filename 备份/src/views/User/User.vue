<template>
  <div class="artist" v-loading="loading">
    <div class="artist-info">
      <div class="artist-img-wrap">
        <img v-lazy="topInfo.picUrl" alt="">
      </div>
      <div class="artist-others">
        <div class="artist-name">{{topInfo.name}}</div>
        <div class="artsit-works">
          <div class="artist-works-count">{{topInfo.desc}}</div>
        </div>
      </div>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="创建的歌单" name="album">
        <ul class="albums">
          <li class="al-item" @click="toMyCollection">
            <div class="al-img-wrap">
              <p class="iconfont icon-play bofang"></p>
              <img v-lazy="topInfo.picUrl" alt="">
            </div>
            <div class="al-name">我的收藏</div>
            <!-- <router-link to="/user" style="text-decoration-line: none; "><div class="al-time" @click="drop(item.id)">删除歌单</div></router-link> -->
          </li>

          <!-- <li class="al-item" v-for="(item,index1) in albumData" :key="index1">
            <div class="al-img-wrap">
              <p class="iconfont icon-play bofang"></p>
              <img v-lazy="item.img" alt="" @click="toAlbum(item.id)">
            </div>
            <div class="al-name" :title="item.title">{{item.title}}</div>
            <router-link to="/user" style="text-decoration-line: none; "><div class="al-time" @click="drop(item.id)">删除歌单</div></router-link>
          </li> -->
        </ul>
      </el-tab-pane>
      
      <el-tab-pane label="个人信息" name="private">
        <!-- 个人信息的表单 -->
        <div style="padding: 0 600px 0 0">
          <el-form label-position="left" label-width="80px" :model="putInfo">
            <el-form-item label="用户名">
              <el-input v-model="putInfo.username" :placeholder="info.username"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="putInfo.password" :placeholder="info.password" show-password></el-input>
            </el-form-item>
            <el-form-item label="签名">
              <el-input v-model="putInfo.signature" :placeholder="info.signature"></el-input>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="putInfo.phone" :placeholder="info.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="putInfo.email" :placeholder="info.email"></el-input>
            </el-form-item>
          </el-form>
          <center>
            <div class="button">
              <el-button type="success" plain @click="commit" size="medium">提交修改</el-button>
            </div>
          </center>
        </div>
      </el-tab-pane>
      
      <el-tab-pane label="登出" name="logout">
      </el-tab-pane>
    </el-tabs>        
  </div>
</template>

<script>
import { userInfoAPI, userSongsAPI, changeInfoAPI, dropSonglistAPI } from '@/utils/api'

export default {
  data(){
      return {
          artistId:'',
          activeName:'album',
          topInfo:{},
          albumData:[],
          mvData:[],
          briefDescList:[],
          introduction:[],
          simi:[],
          loading:true,
          info: {},
          putInfo: {
            username: '',
            password: '',
            signature: '',
            phone: '',
            email: '',
          }
      }
  },
  created(){
      this.artistId = this.$store.state.uid
      this.getAlbumData()
  },
  methods:{
      toMyCollection() {
        this.$router.push('/collection')
      },
      drop(id) {
        dropSonglistAPI(id)
      },
      toAlbum(id) {
        this.$router.push(`myplaylist?id=${id}`)
      },
      handleClick(tab) {
          this.loading = true
          if(tab.label == "登出"){
            this.$store.state.isLogin = false
            this.$store.state.userImg = 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'
            this.$message({
                showClose: true,
                message: '登出成功',
                type: 'success'
            });
            this.$router.push('/login')
          }
          setTimeout(() => {
              this.loading = false
          }, 500);
      },
      getAlbumData(){
        userInfoAPI(this.artistId).then(res=>{
              console.log(res)
              this.topInfo = {
                  name:res.data.data.username,
                  picUrl:res.data.data.img,
                  desc:res.data.data.signature,
              }
              this.info = res.data.data
              this.$store.state.userImg = res.data.data.img
          }).then(()=>{
              this.loading = false
        })
        userSongsAPI(this.artistId).then(res => {
          this.albumData = res.data.data.sheetList
        })
      },   
      commit() {
        for(let prop in this.putInfo) {
          if(this.putInfo[prop] === '') 
            delete this.putInfo[prop]
        }
        changeInfoAPI(this.putInfo).then(res => {
          if(res.data.message === '更新成功') {
            alert('更新成功')
          }
        })
      }                     
  },
  filters:{
  },
  watch:{
      $route(newVal){
          this.artistId = newVal.query.artistId
          this.getAlbumData()

          setTimeout(() => {
              this.activeName = "album"                
              this.loading = false
          }, 0);            
      }
  }    
}
</script>

<style scoped>
.artist-others {
  margin-top: 30px;
}
.artist {
  max-width: 1300px;
  margin: 0 auto;
  padding: 20px;        
}

.artist >>> .el-tabs__item{
    font-size: 14px;
}

.artist >>> .el-loading-spinner {
  top: 15%;
}

.artist-info {
    display: flex;
    font-size: 14px;
    margin-bottom: 20px;
}

.artist-img-wrap {
    width: 200px;
    height: 200px;
    margin-right: 50px;
}

.artist-img-wrap img {
    width: 100%;
    height: 100%;
}

.artist-name {
    font-weight: bold;
    font-size: 22px;
}

.artsit-works {
    margin-top: 20px;
    display: flex;
}

.artist-works-count:nth-of-type(2) {
    margin: 0 20px;
}

/* tabs部分 */
/* 专辑 */
.albums {
    display: grid;
    grid-template-columns: repeat(4,1fr);
    gap: 20px;
    align-items: center;
}

.al-item {
    font-size: 14px;
    width: 250px;
}

.al-img-wrap {
    width: 100%;
    position: relative;
}

.al-img-wrap img {
    width: 100%;
    border-radius: 10px;
}

.al-img-wrap .bofang::before {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    width: 50px;
    height: 50px;
    background-color: rgba(255, 255, 255, .8);
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #c0392b;
    font-size: 20px;
    cursor: pointer;
    opacity: 0;
    transition: .5s;
}

.al-img-wrap:hover .bofang::before{
    opacity: 1;
}

.al-name {
    margin: 5px 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.al-time {
    color: grey;
}

</style>