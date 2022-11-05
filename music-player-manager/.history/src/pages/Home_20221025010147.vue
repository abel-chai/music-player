<template>
  <div class="wrapper">
    <Top/>
    <Sidebar/>
    <div class="content-box" :class="{'content-collapse':collapse}">
      <v-tags/></v-tags>
      <div class="content">
        <transition name="move" mode="out-in">
          <keep-alive :include="tagsList">
            <router-view/>
          </keep-alive>
        </transition>
        <el-backtop target=".content"></el-backtop>
      </div>
    </div>
    <!-- <div class="content">
      <transition name="move" mode="out-in"><router-view/></transition>
    </div> -->
  </div>
</template>

<script>
  import Top from '../components/Top.vue';
  import Sidebar from '../components/Sidebar.vue';

  export default {
    name: 'Home',
    components: { Top, Sidebar}
  }
</script>

<style scoped>
  .wrapper{
    width:100%;
    height:100%;
    overflow: hidden;
  }
  .content{
    background: none repeat scroll 0 0 #fff;
    position: absolute;
    left: 250px;
    right: 0;
    top: 70px;
    bottom:0;
    width: auto;
    padding:40px;
    box-sizing: border-box;
    overflow-y: scroll;
  }
</style>
