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
      <el-tab-pane label="我的歌单" name="album">
        <ul class="albums">
          <li class="al-item" @click="toMyCollection">
            <div class="al-img-wrap">
              <p class="iconfont icon-play bofang"></p>
              <img src="../../assets/imgs/love.png" alt="">
            </div>
            <div class="al-name">我的收藏</div>
          </li>

          <li class="al-item" v-for="(item,index1) in albumData" :key="index1">
            <div class="al-img-wrap">
              <p class="iconfont icon-play bofang"></p>
              <img v-lazy="$store.state.baseURL+item.img" alt="" @click="toAlbum(item.id)">
            </div>
            <div class="al-name" :title="item.title">{{item.title}}</div>
            <router-link to="/user" style="text-decoration-line: none; "><div class="al-time" @click="drop(item.id)">删除歌单</div></router-link>
          </li>
        </ul>
      </el-tab-pane>

      <el-tab-pane label="新建歌单" name="create">
        <div style="padding: 30px 600px 0 0">
          <el-form label-position="left" label-width="80px" :model="putInfo">
            <el-form-item label="歌单名">
              <el-input v-model="createSonglist.title" placeholder="请输入歌单的名字"></el-input>
            </el-form-item>
            <el-form-item label="歌单简介">
              <el-input v-model="createSonglist.intro" placeholder="请输入歌单的简介"></el-input>
            </el-form-item>
            <!-- <el-form-item label="歌曲风格">
              <el-radio v-model="createSonglist.style" label="欧美">欧美</el-radio>
              <el-radio v-model="createSonglist.style" label="华语">华语</el-radio>
              <el-radio v-model="createSonglist.style" label="粤语">粤语</el-radio>
              <el-radio v-model="createSonglist.style" label="日韩">日韩</el-radio>
              <el-radio v-model="createSonglist.style" label="BGM">BGM</el-radio>
              <el-radio v-model="createSonglist.style" label="轻音乐">轻音乐</el-radio>
              <el-radio v-model="createSonglist.style" label="乐器">乐器</el-radio>
            </el-form-item> -->
          </el-form>
          <center>
            <div class="button">
              <el-button type="success" plain @click="createList" size="medium">创建</el-button>
            </div>
          </center>
        </div>
      </el-tab-pane>
      
      <el-tab-pane label="个人信息" name="private">
        <div style="padding: 0 600px 0 0" class="info">
          <el-descriptions v-if="!canChange" class="margin-top" title="我的信息" :column="2">
            <template slot="extra">
              <el-button type="primary" @click="showForm">修改</el-button>
            </template>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                用户名
              </template>
              {{info.username}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-check"></i>
                签名
              </template>
              {{info.signature}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                电话
              </template>
              {{info.phone}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-takeaway-box"></i>
                邮箱
              </template>
              {{info.email}}
            </el-descriptions-item>
          </el-descriptions>
          <el-form label-position="left" label-width="80px" :model="putInfo" v-if="canChange">
            <el-form-item label="用户名">
              <el-input v-model="putInfo.username" ></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="putInfo.password" show-password></el-input>
            </el-form-item>
            <el-form-item label="签名">
              <el-input v-model="putInfo.signature"></el-input>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="putInfo.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="putInfo.email"></el-input>
            </el-form-item>
            <center>
              <div class="button">
                <el-button type="success" plain @click="commit" size="medium">确认修改</el-button>
                <el-button type="primary" plain @click="dontcommit" size="medium">取消修改</el-button>
              </div>
            </center>
          </el-form>
        </div>
      </el-tab-pane>
      
      <el-tab-pane label="账号操作" name="logout">
        <div>
            <el-button type="warning" @click="logOut">退出登录</el-button>
            <el-button type="danger" @click="deleteAccount">注销账号</el-button>
        </div>
      </el-tab-pane>
    </el-tabs>        
  </div>
</template>

<script>
import { userInfoAPI, userSongsAPI, changeInfoAPI, dropSonglistAPI, createListAPI, deleteAccountAPI } from '@/utils/api'

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
            id: '',
            username: '',
            password: '',
            signature: '',
            phone: '',
            email: '',
          },
          createSonglist: {
            title: '',
            intro: '',
            // style: ''
          },
          canChange: false
      }
  },
  created(){
      this.artistId = localStorage.uid
      this.getAlbumData()
  },
  methods:{
      deleteAccount() {
        deleteAccountAPI(localStorage.uid).then(()=>{
          localStorage.setItem('isLogin', false)
              this.$store.state.isLogin = false
              this.$store.state.userImg = 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'
              this.$message({
                  showClose: true,
                  message: '账号注销成功',
                  type: 'success'
              });
              this.$router.push('/discover')
        })
      },
      showForm() {
        this.canChange = ! this.canChange;
      },
      createList() {
        for (let key in this.createSonglist) {
          if(this.createSonglist[key] === '') {
            this.$message({
                showClose: true,
                message: '提交的数据不能为空',
                type: 'error'
            });
            return
          }
        }
        console.log(this.createList);
        createListAPI(localStorage.uid, this.createSonglist).then(res=>{
          if(res.data.type === 'success') {
            this.$message({
                showClose: true,
                message: '创建成功',
                type: 'success'
            });
            
            this.$router.go(0)
          }
        })
      },
      toMyCollection() {
        this.$router.push('/collection')
      },
      drop(id) {
        this.$confirm('是否要删除该歌单?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              dropSonglistAPI(id).then(res => {
                if(res.data.type === 'success') {
                  this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success'
                  });
                }
                userSongsAPI(this.artistId).then(res => {
                  this.albumData = res.data.data.sheetList
                })
              })
              // this.$router.go(0)
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消'
              });          
            });
      },
      toAlbum(id) {
        
        this.$router.push(`myplaylist?id=${id}`)
      },
      handleClick(tab) {
        if(tab.label != '个人信息') {
          this.canChange = false
        }
          
      },
      logOut() {
        this.loading = true
            this.$confirm('将要退出登录, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              localStorage.setItem('isLogin', false)
              this.$store.state.isLogin = false
              this.$store.state.userImg = 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'
              this.$message({
                  showClose: true,
                  message: '登出成功',
                  type: 'success'
              });
              this.$router.push('/discover')
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消'
              });          
            });
          this.loading = false
      },
      dontcommit() {
        this.canChange = !this.canChange
      },
      getAlbumData(){
        userInfoAPI(this.artistId).then(res=>{
              console.log(res)
              this.topInfo = {
                  name:res.data.data.username,
                  picUrl:this.$store.state.baseURL+res.data.data.img,
                  desc:res.data.data.signature,
              }
              this.info = res.data.data
              this.putInfo.username = this.info.username
              this.putInfo.signature = this.info.signature
              this.putInfo.phone = this.info.phone
              this.putInfo.email = this.info.email
              this.$store.state.userImg = this.$store.state.baseURL+res.data.data.img
          }).then(()=>{
              this.loading = false
        })
        userSongsAPI(this.artistId).then(res => {
          this.albumData = res.data.data.sheetList
        })
      },   
      commit() {
        const regs= {
          id: /\w+/,
          username:  /^[a-zA-Z][a-zA-Z0-9_]{3,15}$/,
          password:  /\w{5,17}$/,
          signature: /\w*/,
          phone: /^1[3|4|5|7|8]\d{9}$/,
          email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
        }
        this.putInfo.id = this.artistId
        for(let prop in this.putInfo) {
          console.log(prop);
          if(this.putInfo[prop] === '' && prop != 'id')  {
            delete this.putInfo[prop]
            continue
          }
          else {
            if(!regs[prop].test(this.putInfo[prop])) {
              if(prop == 'username'){
                this.$message({
                  type:'info',
                  message:'请输入正确的用户名',
                  showClose:true
                })
              }
              else if(prop == 'password'){
                this.$message({
                  type:'info',
                  message:'请输入正确的密码',
                  showClose:true
                })
              }
              else if(prop == 'phone'){
                this.$message({
                  type:'info',
                  message:'请输入正确的手机号',
                  showClose:true
                })
              }
              else if(prop == 'email'){
                this.$message({
                  type:'info',
                  message:'请输入正确的邮箱',
                  showClose:true
                })
              }
            }
          }
        }
        changeInfoAPI(this.putInfo).then(res => {
          if(res.data.message === '更新成功') {
            this.$message({
                showClose: true,
                message: '修改成功',
                type: 'success'
            });
            userInfoAPI(this.artistId).then(res=>{
              console.log(res)
              this.topInfo = {
                  name:res.data.data.username,
                  picUrl:this.$store.state.baseURL+res.data.data.img,
                  desc:res.data.data.signature,
              }
              this.info = res.data.data
            })  
            this.canChange = ! this.canChange
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
.info .el-input__inner {
  border: 1px solid #FFF;
}
.info input::placeholder {
  color: black;
}
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