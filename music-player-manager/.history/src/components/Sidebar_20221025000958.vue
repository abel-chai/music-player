<template>
  <div class="sidebar">
    <el-menu 
    default-active="Info" 
    class="el-menu-vertical-demo" 
    theme="dark" unique-opened 
    router
    :collapse="isCollapse"
    >
      <el-menu-item index="Info">
        <i class="el-icon-setting"/>系统首页
      </el-menu-item>
      <el-submenu index="User">
        <template slot="title"><i class="el-icon-user"/>人员管理</template>
        <el-menu-item index="Admin">管理员管理</el-menu-item>
        <el-menu-item index="Client">用户管理</el-menu-item>
      </el-submenu>
      <el-menu-item index="Singer">
        <i class="el-icon-service"/>歌手管理
      </el-menu-item>
      <el-menu-item index="Sheet">
        <i class="el-icon-document"/>歌单管理
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  export default {
    name: 'Sidebar',
    computed:{
        ...map
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
