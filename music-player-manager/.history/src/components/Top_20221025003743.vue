<template>
  <div class="header">
    <div class="collapse-btn" @click="collapseChange">
      <i v-if="!isCollapse" class="el-icon-s-unfold"/>
      <i v-else class="el-icon-s-fold"/>
    </div>
    <div class="logo">后台管理系统</div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- 全屏显示 -->
        <div class="btn-fullscreen" @click="handleFullScreen">
          <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
            <i class="el-icon-rank"></i>
          </el-tooltip>
        </div>
        <!-- 用户头像 -->
        <div class="user-avator">
          <img src="../assets/images/admin.png" />
        </div>
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
          <span class="el-dropdown-link">
            {{username}}
            <i class="el-icon-caret-bottom"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
                    <a href="https://github.com/lin-xin/vue-manage-system" target="_blank">
                        <el-dropdown-item>项目仓库</el-dropdown-item>
                    </a>
                    <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
        </el-dropdown>
        </div>
    </div>
    <div class="user-info">
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          <img class="user-logo" src="../assets/images/admin.png">
          {{username}}
        </span>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="loginOut">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
  import {mapState,mapMutations} from 'vuex'

  export default {
    name: 'Top',
    data() {
      return {
        name: 'abel',
      }
    },
    computed:{
      ...mapState(['isCollapse']),
      username(){
        let username = localStorage.getItem('adminName');
        return username ? username : this.name;
      }
    },
    methods:{
      ...mapMutations(['collapseChange']),
      handleCommand(command) {
        if(command === 'loginOut'){
          localStorage.removeItem('adminName')
          this.$router.push('/');
        }
      }
    }
  }
</script>

<style scoped>
  .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        line-height: 70px;
        color: #fff;
  }
  .header .logo {
    float: left;
    width:150px;
    text-align: center;
  }
  .collapse-btn {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    float: left;
    padding: 0 21px;
    cursor: pointer;
  }
  .user-info {
    float: right;
    padding-right: 50px;
    font-size: 16px;
    color: #fff;
  }
  .user-info .el-dropdown-link{
    position: relative;
    display: inline-block;
    padding-left: 50px;
    color: #fff;
    cursor: pointer;
    vertical-align: middle;
  }
  .user-info .user-logo{
    position: absolute;
    left:0;
    top:15px;
    width:40px;
    height:40px;
    border-radius: 50%;
  }
  .el-dropdown-menu__item{
    text-align: center;
  }
</style>
