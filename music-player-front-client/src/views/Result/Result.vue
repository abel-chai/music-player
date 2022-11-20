<template>
    <div class="result" v-loading="loading">
        <div class="result-top-info">
            <h2>{{keywords}}</h2>
            <span>共找到{{total}}个结果</span>
        </div>
        <div class="tab-wrap">
            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="歌曲" name="first">
                    <div class="songs-table">
                        <el-table
                            :data="songsList"
                            stripe
                            style="width: 100%"
                            @row-dblclick="play">

                            <el-table-column type="index" width="50"></el-table-column>    

                            <el-table-column prop="songName" label="歌曲" width="">
                                <template slot-scope="scope">
                                    <span>{{scope.row.songName}}</span>    
                                </template>
                            </el-table-column>

                            <el-table-column prop="artistInfo" label="歌手" width="">
                                <template slot-scope="scope">
                                    <span class="plus" style="top:15px;" title="收藏歌曲" @click="addToCollection(scope.row,$event)">+</span>
                                    <span style="cursor:pointer;color:#2980b9;display:inline-block" @click="toArtist(scope.row.artistid)">{{scope.row.artistInfo}}</span>                                    
                                </template>                                  
                            </el-table-column> 
                            <el-table-column width="" label="操作">  
                                <template slot-scope="scope">
                                    <span style="top:20px</ul>;" title="添加到歌单">
                                        <el-popover
                                            placement="right"
                                            width="100"
                                            trigger="click">
                                            <p v-for="(item,i) in getData" :key="i" @click="addToMyList(scope.row,i)">{{item.title}}</p>
                                            <el-button slot="reference">添加到歌单</el-button>
                                        </el-popover>
                                    </span>
                                </template> 
                            </el-table-column> 
                        </el-table>                        
                    </div>
                </el-tab-pane>


                <el-tab-pane label="歌单" name="second">
                    <div class="songs-wrap">
                        <div class="list">
                            <ul>
                                <li class="iconfont icon-play" v-for="(item,index) in playList" :key="index" @click="toPlaylistDetail(item.id)">
                                    <p class="first-p"></p>
                                    <img v-lazy="item.img" alt="recommend">
                                    <p class="last-p" :title="item.title">{{item.title}}</p>
                                </li>                                                                                                                    
                            </ul>
                        </div>
                    </div>                    
                </el-tab-pane>
                
            </el-tabs>            
        </div>
        <div class="add-ball iconfont icon-yinfu" v-show="showAddBall" ref="addBall"></div>  
    </div>
</template>

<script scoped>
import { searchAPI,addToCollectionAPI,userSongsAPI,addToListAPI } from '@/utils/api'

export default {
    data(){
        return {
            keywords:"",
            activeName: 'first',
            songsList: [],
            playList:[],
            mvList:[],
            page:1,
            total:0,
            pageSize:10,
            type:1,
            loading:true,
            showAddBall:false,
            musicURL: '',
            getData: []
        }
    },
    components: {
    },
    computed:{
        musicQueue(){
            return this.$store.state.musicQueue
        },
        queuePos(){
            return this.$store.state.queuePos
        }
    },
    methods:{     
        addToMyList(row,i){
            console.log(row);
            const params = {
                id: this.getData[i].id,
                singerName: row.artistInfo,
                songName: row.songName
            }
            console.log(i);
            addToListAPI(params).then(()=>{
                this.$message({
                    showClose: true,
                    message: '添加成功',
                    type: 'success'
                });
            })
        },
        addToCollection(row) {
            if (localStorage.isLogin != 'true') {
                this.$confirm('收藏歌曲需要登录, 是否前往登录?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                    this.$router.push('/login')
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消'
                        });          
                });
                return
            }
            const params = {
                clientId: localStorage.uid,
                songId: row.id,
                type: 1
            }
            addToCollectionAPI(params)
            this.$message({
                showClose: true,
                message: '收藏成功',
                type: 'success'
            });
        },       
        toPlaylistDetail(id){
            this.$router.push(`/playlist?id=${id}`)
        },
        toArtist(id){
            this.$router.push(`/artist?artistId=${id}`)
        },                       
        handleCurrentChange(page){
            this.page = page
            this.getTableData(this.type)
        },
        handleClick(tab) {
            let label = tab.label
            switch(label){
                case '歌曲':
                    this.total = 0
                    this.page = 1
                    this.type = 1
                    this.pageSize = 10
                    this.getTableData()
                    break;
                case '歌单':
                    this.total = 0                    
                    this.page = 1                    
                    this.type = 1000
                    this.pageSize = 10                      
                    this.getTableData(this.type)
                    break;
            }
        },
        play(row){
            console.log('row');
            console.log(row);
            let id = row.pid
            this.songUrl = this.songsList[id].url

            let musicInfo = {
                imgUrl:row.imgUrl,
                artistInfo:row.artistInfo,
                songName:row.songName,
                id:row.id,
            }

            this.$store.commit("changeMusicUrl",this.songUrl)
            this.$store.commit("changeMusicInfo",musicInfo)
            this.$store.commit('changeMusicStatus',false)
            this.$store.commit('changeMusicQueue',musicInfo)

            let ids = []
            for (const item of this.musicQueue) {
                ids.push(item.id)
            }
            this.$store.commit("changeNowIndex",ids.indexOf(musicInfo.id))           
        },
        getTableData(type=1){
            this.loading = true
            let stype = ''
            if(type === 1) stype='song'
            else stype = 'sheet'

            searchAPI(stype, this.keywords).then(res=>{
                console.log(res)
                let resultList = []

                switch(type){
                    case 1:
                        resultList = res.data.data
                        console.log(resultList);
                        if(this.total == 0)
                            this.total = res.data.data.length
                        var songsList = []

                        for (let index in resultList) {
                            var song = {
                                id:resultList[index].song.id,
                                songName:resultList[index].song.songName,
                                artistInfo:resultList[index].singer.singerName,
                                imgUrl:resultList[index].song.img,
                                artistid: resultList[index].singer.id,
                                url:resultList[index].song.url,
                                pid: index
                            }
                            songsList.push(song)
                        }
                        this.songsList = songsList                                                
                        break
                    case 1000:
                        resultList = res.data.data
                        if(this.total == 0)
                            this.total = res.data.data.length
                        this.playList = resultList    
                        for(let item of this.playList) {
                            item.img = this.$store.state.baseURL+item.img
                        }          
                        console.log(this.playList);            
                        break
                }
            }).then(()=>{
                this.loading = false
            })  
            userSongsAPI(localStorage.uid).then(res=>{
                this.getData = res.data.data.sheetList
            })
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
    },
    created(){
        this.keywords = this.$route.query.keywords        
        this.getTableData()
    },
    watch:{
        $route(newVal){
            this.keywords = newVal.query.keywords
            this.total = 0
            this.page = 1                
            this.getTableData(this.type)
        }
    }
}
</script>

<style>
    @import '../../assets/common/tab.css';

    .add-ball {
        position: fixed;
        color: rgb(236, 65, 65);
        transition: top .5s ease-in,left .5s linear;
        z-index: 9999;
    }

    ul {
        list-style: none;
    }

    .result {
        max-width: 1300px;
        margin: 0 auto;
        padding: 20px;
    }

    .result-top-info {
        margin-bottom: 20px;
        display: flex;
        align-items: flex-end;
    }

    .result-top-info span {
        margin-left: 10px;
        font-size: 12px;
        color: grey;
    }
    
    .el-tabs__item {
        font-size: 18px;
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

    .mvIcon {
        color: rgb(236, 65, 65);
        margin-left: 5px;
        cursor: pointer;
    }


    /* 歌单Tab */
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
      /* font-size: 35px; */
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


/* MVTabs */
  .mv-list1 {
      display: flex;
      justify-content: flex-start;
      flex-wrap: wrap;
  }

  .mv-list1 li {
      width: 23%;
      margin-bottom: 20px;
      margin-right: 20px;
  }

  .mv-img-wrap {
      position: relative;
      cursor: pointer;
  }

  .mv-img-wrap img{
    width: 100%;
    height: 165px;
    border-radius: 5px;    
  }

  .mv-img-wrap:hover .play::before {
      opacity: 1;
  }

  .mv-img-wrap .play::before {
      content: "\e665"; 
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
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

  .play-count{
      position: absolute;
      top: 5px;
      right: 5px;
      color: #fff;
      font-size: 12px;
  }

  .play-count::before {
      margin-right: 5px;
  }

  .mv-duration {
      position: absolute;
      bottom: 10px;
      right: 5px;
      color: #fff;
      font-size: 12px;
  }

 .mv-info p{
     margin: 5px 0;
 }

 .mv-info p.title {
   overflow: hidden;
   text-overflow: ellipsis;
   white-space: nowrap;
 }

  .mv-info .author{
      color: #a5a1a1;
      font-size: 12px;
  }
</style>