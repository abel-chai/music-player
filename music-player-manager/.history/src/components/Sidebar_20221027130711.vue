<template>
  <div class="sidebar">
    <el-menu 
    default-active="Info" 
    class="sidebar-el-menu"
    background-color="#324157"
    text-color="#bfcbd9"
    active-text-color="#20a0ff"
    unique-opened 
    router
    :collapse="isCollapse"
    >
      <el-menu-item index="Info">
        <i class="el-icon-setting"/>
        <span slot="title">系统首页</span>
      </el-menu-item>
      <template>
        
      </template>

      <el-submenu index="User">
        <template slot="title">
          <i class="el-icon-user"/>
          <span slot="title">人员管理</span>
        </template>
        <el-menu-item index="Admin">管理员管理</el-menu-item>
        <el-menu-item index="Client">用户管理</el-menu-item>
      </el-submenu>
      <el-menu-item index="Singer">
        <i class="el-icon-service"/>
        <span slot="title">歌手管理</span>
      </el-menu-item>
      <el-menu-item index="Sheet">
        <i class="el-icon-document"/>
        <span slot="title">歌单管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
    name: 'Sidebar',
    computed:{
        ...mapState(['isCollapse'])
    }
  }
</script>

<style scoped>
  .sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
  }
  .sidebar::-webkit-scrollbar {
    width: 0;
  }
  .sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
  }
  .sidebar > ul {
    height: 100%;
  }  
</style>
