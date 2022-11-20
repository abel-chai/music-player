<template>
  <div class="playlist"  v-loading="loading">
    <div class="artist-info">
      <div class="artist-img-wrap">
        <img v-lazy="playlistInfo.img" alt="">
      </div>
      <div class="artist-others">
        <div class="artist-name">{{playlistInfo.username}}</div>
        <div class="artsit-works">
          <div class="artist-works-count">{{playlistInfo.signature}}</div>
        </div>
      </div>
    </div>

      <div class="playlist-tabs-wrap">
          <el-tabs v-model="activeName">
              <el-tab-pane label="歌曲列表" name="first">
                  <div class="songs-table">
                      <el-table
                          :data="tableData"
                          stripe
                          @row-dblclick="play"
                          v-el-table-infinite-scroll="loadMore"
                          infinite-scroll-delay=500
                          infinite-scroll-disabled="noMore">

                          <el-table-column type="index" width="50"></el-table-column>

                          <el-table-column width="100">
                              <template slot-scope="scope">
                              <div class="img-wrap">
                                  <img v-lazy="$store.state.baseURL+scope.row.song.img" alt="">
                                  <p class="iconfont icon-play"  @click="play(scope.row)"></p>                                
                              </div>                                
                              </template>
                          </el-table-column>     

                          <el-table-column prop="song.songName" label="歌曲" width=""></el-table-column>
                          <el-table-column prop="singer.singerName" label="歌手" width="">  
                              <template slot-scope="scope">
                                  <span style="cursor:pointer;color:#2980b9;" @click="$router.push(`/artist?artistId=${scope.row.singer.id}`)">{{scope.row.singer.singerName}}</span>                                                          
                                  <span class="plus" title="取消收藏" @click="dropFromCollection(scope.row)" style="top:20px;color:red;">❤</span>
                              </template>
                          </el-table-column>                            
                      </el-table>
                  </div>                    
              </el-tab-pane>
          </el-tabs>     
      </div>
  </div>
</template>

<script>
import elTableInfiniteScroll from 'el-table-infinite-scroll'
import { myCollectionAPI,dropFromCollectionAPI } from '@/utils/api'

export default {
  data(){
      return {
          activeName:'first',
          playlistInfo:{},
          trackIds:[],
          tableData:[],
          total:0,
          pageSize:10,
          page:1,
          playlistId:"",
          loading:true,
          loadBegin:0,
          allData:[],
          showAddBall:false,
          songList: [],
      }
  },
  components: {
  },
  directives:{
      'el-table-infinite-scroll':elTableInfiniteScroll
  },
  created(){
      console.log(this.$route.query.id)
      let playlistId = this.$route.query.id
      this.playlistId = playlistId

      this.getTableData()
  },
  computed:{
      noMore(){
          return this.tableData.length >= this.trackIds.length
      },
      nowIndex(){
          return this.$store.state.nowIndex
      },
      musicQueue(){
          return this.$store.state.musicQueue
      },
      queuePos(){
          return this.$store.state.queuePos
      }        
  },
  methods:{
      //todo
      dropFromCollection(row) {
        const params = { cid: localStorage.uid, sid: row.song.id}
        dropFromCollectionAPI(params).then(()=>{
            myCollectionAPI(localStorage.uid).then(res=>{
              this.songList = res.data.data.songList
            })
            this.$message({
                showClose: true,
                message: '已取消收藏',
                type: 'success'
            });
            this.$router.go(0)
        })
      },
      toArtist(id){
          this.$router.push(`/artist?artistId=${id}`)
      },              
      handleCurrentChange(page){
          this.page = page
      },
      loadMore(){
          console.log('滚动加载')
          this.loading = true
          this.loadBegin += 20
          this.getTableData()
      },
      beginAnimation(x,y){
          this.showAddBall = true
          let endX = this.queuePos.left
          let endY = this.queuePos.top
          console.log('起始位置 x:'+x+' y:'+y)
          console.log('终点位置 x:'+endX+' y:'+endY)

          let el = document.querySelector(".add-ball")
          el.style.left = x+'px'
          el.style.top = y+'px'
          
          setTimeout(() => {
              el.style.left = endX+'px'
              el.style.top = endY+'px'
          }, 0);

          setTimeout(() => {
              this.showAddBall = false
          }, 500);
      },                
      getTableData(){
        myCollectionAPI(localStorage.uid).then(res=>{
              this.playlistInfo = res.data.data.client
              this.playlistInfo.img = this.$store.state.baseURL+this.playlistInfo.img
              this.songList = res.data.data.songList
              
              Promise.all([this.getTracks(false)]).then(()=>{
                  console.log('1111')
              })

          }).then(()=>{
              this.loading = false
          })
      },                
      getTracks(all=false){
          let pushList = []
          pushList = this.songList

          if(all){
              this.allData = this.allData.concat(pushList)
          }else{
              this.tableData = this.tableData.concat(pushList)
          }
      },
      play(row){
          console.log(row)                

          this.songUrl = row.song.url
          
          let musicInfo = {
              imgUrl:row.song.img,

              artistInfo:row.singer.singerName,
              songName:row.song.songName,
              id:row.song.id,
              // duration:row.dt              
          }

          this.$store.commit("changeMusicUrl",this.songUrl)
          this.$store.commit("changeMusicInfo",musicInfo)
          this.$store.commit("changeMusicStatus",false)
          this.$store.commit("changeMusicQueue",musicInfo)

          let ids = []
          for (const item of this.musicQueue) {
              ids.push(item.id)
          }
          this.$store.commit("changeNowIndex",ids.indexOf(musicInfo.id))
      },        
  },  
}
</script>

<style scoped>
  @import '../../assets/common/tab.css';
  .artist-others {
  margin-top: 30px;
}
.artist {
  max-width: 1300px;
  margin: 0 auto;
  padding: 20px;        
}

.artist >>> .el-tabs__item{
    font-size: 14px;
}

.artist >>> .el-loading-spinner {
  top: 15%;
}

.artist-info {
    display: flex;
    font-size: 14px;
    margin-bottom: 20px;
}

.artist-img-wrap {
    width: 200px;
    height: 200px;
    margin-right: 50px;
}

.artist-img-wrap img {
    width: 100%;
    height: 100%;
}

.artist-name {
    font-weight: bold;
    font-size: 22px;
}

.artsit-works {
    margin-top: 20px;
    display: flex;
}

.artist-works-count:nth-of-type(2) {
    margin: 0 20px;
}
  .add-ball {
      position: fixed;
      color: rgb(236, 65, 65);
      transition: top .5s ease-in,left .5s linear;
  }

  ul {
      list-style: none;
  }

  .plus {
      padding: 10px;
      border-radius: 50%;
      margin-left: 10px;
      font-size: 25px;
      position: absolute;
      left: -80px;
      top: 0;
      font-weight: bold;
      cursor: pointer;
      display: none;
  }

  .songs-table .el-table__row:hover .plus{
      display: block;
  }

  .playlist {
    max-width: 1300px;
    margin: 0 auto;
    padding: 20px;        
  }

  .playlist-top-card {
      display: flex;
      /* height: 300px; */
  }

  .playlist-img-wrap {
      width: 200px;
      height: 200px;
  }

  .playlist-img-wrap img {
      width: 100%;
      height: 100%;
  }

  .playlist-info {
      /* font-size: 16px; */
      padding: 0 20px;
      flex: 1;
  }

  .playlist-info .tag1 {
      color: rgb(208, 53, 53);
      border: 1px solid rgb(208, 53, 53);
      display: inline-block;
      font-size: 20px;
      margin-right: 10px;
      border-radius: 5px;
      padding: 0px 7px;
      cursor: default;
  }

  .playlist-name {
      font-size: 25px;
      font-weight: 600;
      display: flex;
      align-items: center;
      display: block;
      margin-bottom: 25px;
  }

  .playlist-user-info {
      display: flex;
      align-items: center;
      font-size: 14px;
  }

  .playlist-user-info img {
      margin: 10px 0;
  }

  .playlist-user-avatar img {
      height: 30px;
      width: 30px;
      border-radius: 50%;
  }

  .playlist-user-name {
      margin: 0 10px;
  }

  .playAllBtn {
      display: inline-block;
      margin-right: 10px;
      border-radius: 20px;
      background: #d03535;
      padding: 5px 15px;
      color: #fff;
      cursor: pointer;
  }

  .playlist-tags {
      margin: 10px 0;
  }

  .playlist-desc {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
  }

  .playlist-desc span:nth-of-type(2){
      font-size: 14px;
  }

  .playlist-tags .tags {
      font-size: 14px;
  }

  .playlist-tags .tags:not(:last-of-type)::after {
      content: '/';
      margin: 0 4px;
  }

  .playlist-tabs-wrap {
      margin-top: 20px;
  }

.el-table td, .el-table th.is-leaf {
  border-bottom: none;
}

.el-table::before {
  opacity: 0;
}

.songs-table {
  width: 100%;
}

  /* 设置滚动条样式 */
  /* 滚动条整体样式 */
  .songs-table>>>::-webkit-scrollbar{
      width: 5px;
      height: 1px;
  }
  /* 滚动条里面的小方块 */
  .songs-table>>>::-webkit-scrollbar-thumb{
      border-radius: 4px;
      box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
      background: #f2f2f2;
  }
  /* 滚动条里面的轨道 选择不显示 */
  .songs-table>>>::-webkit-scrollbar-track{
      box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
      border-radius: 4px;
      background: #fff;
  }

.img-wrap {
  width: 60px;
  height: 60px;
  position: relative;
}

.img-wrap img {
  width: 100%;
  height: 100%;
  border-radius: 10px;
}

.img-wrap p::before {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  width: 30px;
  height: 30px;
  background-color: rgba(255, 255, 255, .8);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #c0392b;
  font-size: 14px;
  cursor: pointer;
}

</style>