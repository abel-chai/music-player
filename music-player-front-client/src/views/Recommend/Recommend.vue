<template>
  <div class="recommend" v-loading="loading">
    <div class="top-card-wrap">
      <img src="../../assets/imgs/jj.jpg" class="bg-blur" alt="bg-blur">
      <div class="top-card">
        <div class="img-wrap">
          <img src="../../assets/imgs/jj.jpg" alt="img">
        </div>
        <div class="card-content">
          <div class="card-tag">歌单推荐</div>
          <div class="card-title">林俊杰的正确打开方式【路人请参考简介】</div>
          <div class="card-info">
            <br>这是一个可以完美呈现林俊杰音乐态度的歌单，是一个林俊杰的立体化打开方式。
            <br>
            <br>阳光而爽朗，深情而执着，从新加坡走出的林俊杰以这样的初印象进入了华语流行世界。
            <br>· 2003 年林俊杰以颇具好评的《乐行者》出道。
            <br>· 2004 年紧随其后的专辑《第二天堂》中，他又以堪称现象级的传世之作《江南》打开了前景，一曲成名，林俊杰的实力开始被越来越多的人关注。
            <br>尽管乐坛风云更迭，林俊杰却一直在以稳扎稳打的努力回馈歌迷的支持，逐步成为华语乐坛的唱作人标杆，用《她说》《修炼爱情》以及《爱笑的眼睛》为华语流行乐留下浓墨重彩的金曲记忆。
            <br>林俊杰有着健康、乐观的形象与积极的音乐态度，他的唱功出众，音色辨识度极高，堪称“行走的 CD 机”。而平易近人的作风、亲切的气质，又使得林俊杰成为“宝藏艺人”的代名词。
            <br>无论是在唱片主导的年代，还是以流媒体为主要收听方式的当下，林俊杰的创作才华与人格魅力征服了各个世代的歌迷群体，发展至今，说他是华语乐坛的标志性人物可谓名副其实。
            <br>林俊杰的歌有时热情似火，有时深情款款，比如《背对背拥抱》中贴近的心跳，又比如《小酒窝》中融化的恋爱心情；心碎时有《Wonderland》等你降落，抚慰疗伤；脚步沉重时有《进阶》让你振作，重新启程。他的音乐中有着无微不至的关怀，他的歌为平凡的人不平凡的勇气和情感而唱。这份歌单收入林俊杰近二十年音乐历程中的里程碑作品，带你深入他的音乐世界。            

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
                        <p class="first-p"></p>
                        <img v-lazy="$store.state.baseURL+item.img" alt="songLists">
                        <p class="last-p">{{item.title}}</p>
                    </li>                                                                                                                    
                </ul>
            </div>
        </div>        
      </div>
    </div>
  </div>
</template>

<script>
import { recommendSonglistAPI } from '@/utils/api'

export default {
  data(){
    return{
      songLists:[],
      tabActive:'全部',
      tabItems:['全部','欧美','华语','粤语','日韩','BGM','轻音乐','乐器'],
      total:0,
      loading:true
    }
  },
  components: {
    
  },
  methods:{
    toPlaylistDetail(id){
        this.$router.push(`/playlist?id=${id}`)
    },    
    changeActive(item){
      this.tabActive = item
      this.getSongLists(item) /* 不调用函数 也可以使用watch监听实现 */
    },
    getSongLists(style=''){
      this.loading = true
      let url = ''
      if(style != '' && style != '全部') url = `style/${style}`
      recommendSonglistAPI(url).then(res=>{
        this.songLists = res.data.data
        this.total = res.data.data.length /* 改变总页数 */  
      }).then(()=>{
        this.loading = false
      })
    },
  },
  created(){
    this.getSongLists()      
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

  .songs-wrap ul img {
      width: 100%;
      border-radius: 5px;
      /* opacity: 1; */
  }

  .songs-wrap ul .last-p {
      font-size: 14px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
  } 
</style>