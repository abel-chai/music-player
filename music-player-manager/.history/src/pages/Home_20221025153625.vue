<template>
  <div class="wrapper">
    <Top/>
    <Sidebar/>
    <div class="content-box" :class="{'content-collapse':collapse}">
    <div class="content">
      <transition name="move" mode="out-in">
        <router-view/>
      </transition>
      <el-backtop target=".content"></el-backtop>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex';
  import Top from '../components/Top.vue';
  import Sidebar from '../components/Sidebar.vue';

  export default {
    name: 'Home',
    computed:{
      ...mapState(['collapse'])
    },
    components: { Top, Sidebar}
  }
</script>
