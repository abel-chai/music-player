<template>
  <div class="sidebar">
    <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" theme="dark" unique-opened router>
      <el-menu-item index="info">
        <i :class="el-icon-star-on"/>系统首页
      </el-menu-item>
      <el-submenu index="user">
        <template slot="title"><i :class="el-icon-lx-people"/>人员管理</template>
        <el-menu-item index="admin">管理员管理</el-menu-item>
        <el-menu-item index="client">用户管理</el-menu-item>
      </el-submenu>
      <el-menu-item index="singer">
        <i :class="el-icon-lx-service"/>歌手管理
      </el-menu-item>
      <el-menu-item index="songList">
        <i :class="el-icon-lx-text"/>歌单管理
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  export default {
    computed:{
      onRoutes(){
        return this.$route.path.replace('/','');
            }
        }
    }
</script>

<style scoped>
  .sidebar{
    display: block;
    position: absolute;
    width: 250px;
    left: 0;
    top: 70px;
    bottom:0;
    background: #2E363F;
  }
  .sidebar > ul {
    height:100%;
  }
</style>
