<template>
  <div class="index">
      <div class="index-left">
          <ul>
              <li>
                  <router-link to="/discover"><span class="iconfont2" style="font-size: 30px;">&#xe76a;</span>
                    <span class="title">&nbsp;发现音乐</span></router-link>
              </li>
              <li>
                  <router-link to="/singer"><span class="iconfont2">&#xe65f;</span>
                    <span class="title">发现歌手</span></router-link>              
              </li>
              <li>
                  <router-link to="/recommend"><span class="iconfont2">&#xe8f0;</span>
                    <span class="title">&nbsp;&nbsp;热门歌单</span></router-link>           
              </li>
              <li>
                  <router-link to="/user"><span class="iconfont2">&#xe6b8;</span>
                    <span class="title">&nbsp;&nbsp;我的音乐</span></router-link>           
              </li>
          </ul>
      </div>
      <main>
          <el-scrollbar style="height:100%">
            <slot></slot>
          </el-scrollbar>
      </main>
  </div>
</template>

<script>
export default {
    data(){
        return {
        }
    }
}
</script>

<style>
    .title {
        letter-spacing: 0;
        font-size: 15px;
    }
    .index {
        /* height: 800px; */
        display: flex;
    }

    .index-left {
        width: 200px;
        min-width: 200px;
        /* background-color: #ededed; */
        text-align: center;
        border-right: 1px solid #ededed;
        padding-top: 20px;
    }

    .index-left ul {
        list-style: none;
    }

    .index-left li {
        cursor: pointer;
        letter-spacing: .3rem;
        height: 60px;
        line-height: 60px;
        display: flex;
    }

    .index-left li:hover {
        background-color: #ededed;
    }

    .index-left a {
        text-decoration: none;
        color: #333;
        display: block;
        width: 100%;
        height: 100%;
        font-size: 18px;
        padding-left: 30px; 
        text-align: left;  
    }

    .index-left a::before {
        margin-right: 10px;
    }

    .router-link-active {
        color: #e74c3c !important;
    }

    main {
        flex: 1;
        min-width: 750px;
        /* height: 800px; */
        /* overflow-y: scroll; */
        /* background-color: skyblue; */
    }
</style>