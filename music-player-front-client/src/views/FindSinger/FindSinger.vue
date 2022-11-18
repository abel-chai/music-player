<template>
  <div class="recommend" v-loading="loading">
    <div class="top-card-wrap">
      <img src="../../assets/imgs/hoyo.png" class="bg-blur" alt="bg-blur">
      <div class="top-card">
        <div class="img-wrap">
          <img src="../../assets/imgs/hoyo.png" alt="img">
        </div>
        <div class="card-content">
          <div class="card-tag">歌手推荐</div>
          <div class="card-title">HOYO-MiX</div>
          <div class="card-info">
            HOYO-MiX为米哈游（miHoYo）旗下的国人原创音乐团队，主理人为蔡近翰Zoe。团队为米哈游旗下《崩坏学园》、《崩坏学园2》、《崩坏3》、《原神》、《未定事件簿》等游戏项目制作歌曲与配乐，创作的歌曲在国内音乐平台各类榜单、iTunes多国歌曲排行榜中均有不错排名。
            <br>· 2020年9月20日，《原神》于6月发布的首张OST《原神-风与异乡人 Le Vent et les Enfants des étoiles》获得AppleMusic推荐。
            <br>· 2020年9月28日，《原神》公测纪念OST-蒙德篇专辑《原神-风与牧歌之城 City of Winds and Idylls》上线网易云音乐及Apple Music，共计收录63首蒙德地区创作的原声音乐。 [13] 
            <br>· 2020年11月6日， 《原神》璃月篇OST《原神-皎月云间之梦 Jade Moon Upon a Sea of Clouds》上线网易云音乐及Apple Music ，收录69首璃月地区创作的原声音乐，璃月篇精选曲目由伦敦爱乐乐团、上海交响乐团与民乐艺术家演奏，分别于AiRStudios录音棚、上海交响乐团音乐厅完成录制。
            <br>......
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
                        <img v-lazy="item.img" alt="songLists">
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