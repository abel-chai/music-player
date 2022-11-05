<template>
  <div class="wrapper">
    <Top/>
    <Sidebar/>
    <div class="content" :class="{'content-collapse':collapse}">
      div class="content">
        
      </div><
    </div>
  </div>
</template>

<script>
  import mapState from 'vuex';
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
