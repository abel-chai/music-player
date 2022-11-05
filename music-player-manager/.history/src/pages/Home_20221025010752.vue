<template>
  <div class="wrapper">
    <Top/>
    <Sidebar/>
    <div class="content-box" :class="{'content-collapse':collapse}">
      <v-tags/>
      <div class="content">
        <transition name="move" mode="out-in">
          <keep-alive :include="tagsList">
            <router-view/>
          </keep-alive>
        </transition>
        <el-backtop target=".content"></el-backtop>
      </div>
    </div>
  </div>
</template>

<script>
  import mapState from 'vuex', 
  import Top from '../components/Top.vue';
  import Sidebar from '../components/Sidebar.vue';

  export default {
    name: 'Home',
    data() {
      return {
        tagsList: []
      };
    },
    computed:{
      ...mapState(['collapse'])
    },
    components: { Top, Sidebar}
  }
</script>
