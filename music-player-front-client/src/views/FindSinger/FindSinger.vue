<template>
  <div class="recommend" v-loading="loading">
    <div class="top-card-wrap">
      <img src="../../assets/imgs/jackzhou.jpg" class="bg-blur" alt="bg-blur">
      <div class="top-card">
        <div class="img-wrap">
          <img src="../../assets/imgs/jackzhou.jpg" alt="img">
        </div>
        <div class="card-content">
          <div class="card-tag">歌手推荐</div>
          <div class="card-title">周杰伦</div>
          <div class="card-info">
            外文名：Jay Chou
            <br>别名：周董、President Chou
            <br>国籍：中国
            <br>出生地：台湾省新北市
            <br>职业：音乐人、制作人、导演、企业家等
            <br>出生日期：1979年1月18日（农历腊月二十）
            <br>代表作品：《龙卷风》、《菊花台》、《青花瓷》、《晴天》
            <br>主要成就：四届世界音乐大奖最畅销亚洲艺人、中华区8次年度销量冠军（最多）、悉尼演唱会票房美国公告牌第二、美国CNN亚洲最具影响力人物、2012年福布斯中国名人榜第一、MTV日本录影带奖亚洲杰出艺人、两届MTV亚洲大奖最受欢迎艺人、连续7年IFPI十大销量国语唱片、华语乐坛十年领袖人物、华语乐坛十年最佳男歌手、中国十大青年领袖、MTV亚洲大奖最受欢迎男歌手、美国MTV电影最佳新人提名、世界十大鬼才音乐人之一、QQ音乐年度盛典最佳全能艺人
            <br>简介：周杰伦（Jay Chou），1979年1月18日出生于台湾省新北市，华语流行男歌手、演员、词曲创作人、MV及电影导演、编剧及制作人。2000年被吴宗宪发掘，发行首张个人专辑《Jay》。
          </div>
        </div>
      </div>
    </div>
    <div class="tab-container">
      <div class="tab-bar">
        <ul>
          <li :class="item == tabActive?'tab-item active':'tab-item'" v-for="(item,index) in tabItems" :key="index" @click="changeActive(item)">{{item}}</li>
        </ul>
      </div>
      <div class="tab-content">
        <div class="songs-wrap">
            <div class="list">
                <ul>
                    <li class="iconfont icon-play" v-for="(item,index) in songLists" :key="index" @click="toPlaylistDetail(item.id)" >
                        <p class="first-p"> </p>
                        <div class="img" alt="songLists" :style="`background-image: url(${$store.state.baseURL+item.img});`"></div>
                        <p class="last-p">{{item.singerName}}</p>
                    </li>                                                                                                           
                </ul>
            </div>
        </div>        
      </div>
    </div>
  </div>
</template>

<script>
import { singerAPI } from '@/utils/api'

export default {
  data(){
    return{
      songLists:[],
      tabActive:'全部',
      tabItems:["全部","男","女","中国","韩国","意大利","新加坡","美国","西班牙","日本"],
      total:0,
      page:1,
      pageSize: 10,
      loading:true
    }
  },
  components: {
  },
  methods:{
    toPlaylistDetail(id){
        this.$router.push(`/artist?artistId=${id}`)
    },    
    changeActive(item){
      this.tabActive = item
      if(this.tabActive === '全部') item = ''
      else if(this.tabActive === '男' || this.tabActive === '女') item = `search/sex/${this.tabActive}`
      else item = `search/country/${this.tabActive}`
      this.getSongLists(item) /* 不调用函数 也可以使用watch监听实现 */
    },
    getSongLists(item){
      this.loading = true
      singerAPI(item).then(res=>{
        this.songLists = res.data.data
        this.total = res.data.total /* 改变总页数 */
      }).then(()=>{
        this.loading = false
      })
    },
  },
  created(){
    this.getSongLists('')      
  }
}
</script>

<style scoped>
  ul {
    list-style: none;
  }

  .recommend {
      max-width: 1300px;
      margin: 0 auto;
      padding: 20px;
  }
  
  .top-card-wrap {
    height: 250px;
    padding: 20px;
    position: relative;
    z-index: 1;
    box-sizing: border-box;
    border-radius: 10px;
    overflow: hidden;
    display: flex;
    align-items: center;
  }

  .bg-blur {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    filter: blur(20px);
    z-index: 2;
  }

  .top-card {
    display: flex;
    position: absolute;
    z-index: 3;
  }

  .img-wrap {
    width: 200px;
    height: 200px;
  }

  .img-wrap img {
    width: 100%;
    height: 100%;
  }

  .card-content {
    flex: 1;
    padding: 0 20px;
  }

  .card-tag {
    padding: 5px;
    width: 100px;
    box-sizing: border-box;
    border: 1px solid #dfac67;
    color: #dfac67;
    text-align: center;
    border-radius: 10px;
    cursor: pointer;
  }

  .card-title {
    color: #fff;
    margin: 10px 0;
  }

  .card-info {
    font-size: 12px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 8;
    /* color: #888482; */
    color: #e3e3e3;
  }

  /* tab导航 */
  .tab-container {
    margin-top: 20px;
  }

  .tab-bar ul {
    height: 25px;
    display: flex;
    justify-content: flex-end;
  }

  .tab-item {
    margin-left: 20px;
    cursor: pointer;
    font-size: 15px;
    color: grey;
  }

  .active {
    color: #dd6d60;
  }

  .tab-content {
    margin-top: 20px;
  }

  .songs-wrap .list ul{
      width: 100%;
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
  }

  .songs-wrap .list li {
      width: 18%;
      margin: 10px 0;
      position: relative;
      overflow-y: hidden;
  }

  .list li .first-p {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      background-color: rgba(0, 0, 0, .5);
      color: #fff;
      font-size: 12px;
      padding: 5px;
      box-sizing: border-box;
      /* border-top-left-radius: 10px;
      border-top-right-radius: 10px; */
      transform: translateY(-100%);;
      transition: .5s;
  }

  .list li::before {
      content: "\e665"; 
      position: absolute;
      bottom: 25px;
      right: 5px;
      width: 35px;
      height: 35px;
      background-color: rgba(255, 255, 255, .8);
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      color: #c0392b;
      opacity: 0;
      transition: .3s;
      cursor: pointer;
  }

  .list li:hover .first-p{
      transform: translateY(0);
  }

    .list li:hover::before{
        opacity: 1;
  }

  .songs-wrap ul .img {
      width: 100%;
      height: 0;
      border-radius: 5px;
      padding-bottom: 100%;
      background-size: 100%;
      /* opacity: 1; */
  }

  .songs-wrap ul .last-p {
      font-size: 14px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
  } 
</style>