<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1">
            <i class="el-icon-user grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{userCount}}</div>
              <div>用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-2">
            <i class="el-icon-headset grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{songCount}}</div>
              <div>歌曲总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-3">
            <i class="el-icon-service grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{singerCount}}</div>
              <div>歌手总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-4">
            <i class="el-icon-document grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{sheetCount}}</div>
              <div>歌单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12" align="center">
        <h3>用户性别比例</h3>
        <el-card class="cav-info-2" shadow="hover" :body-style="{ padding: '0px' }" id="userVar"></el-card> 
        <h3>歌手国籍</h3>
        <el-card class="cav-info-2" shadow="hover" :body-style="{ padding: '0px' }" id="singerVar"></el-card> 
      </el-col>
      <el-col :span="12" align="center"> 
        <h3>歌单类型</h3>
        <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="sheetVar"></el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default{
    name:'info',
    data(){
      return{
        userCount: 10,
        songCount: 100,
        singerCount: 30,
        sheetCount: 20,
        sheetOption: {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '40',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: [
                { value: 1011, name: '华语' },
                { value: 735, name: '粤语' },
                { value: 580, name: '欧美' },
                { value: 484, name: '日韩' },
                { value: 300, name: 'BGM' },
                { value: 200, name: '轻音乐'},
                { value: 200, name: '乐器'}
              ]
            }
          ]
        },
        userOption: {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '40',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: [
                { value: 1048, name: '男' },
                { value: 735, name: '女' },
              ]
            }
          ]
        },
        singerOption: {
          xAxis: {
            type: 'category',
            data: ['中国', '美国', '韩国', '日本', '意大利', '新加坡', '西班牙']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: [10, 5, 3, 2, 4, 8, 6],
              type: 'bar'
            }
          ]
        }
      }
    },
    computed:{
      ...mapState(['token'])
    },
    methods:{
      draw(){
        // 获取用户数量
        this.$axios({
          mathod: 'get',
          url: '/client/count',
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.userCount = response.data.data
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
        //获取歌手数量
        this.$axios({
          mathod: 'get',
          url: '/singer/count',
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.singerCount = response.data.data
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
        // 获取歌曲数量
        this.$axios({
          mathod: 'get',
          url: '/song/count',
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.songCount = response.data.data
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
        // 获取歌单数量
        this.$axios({
          mathod: 'get',
          url: '/sheet/count',
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.sheetCount = response.data.data
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
        // 获取歌单类别
        this.$axios({
          mathod: 'get',
          url: '/sheet/style',
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              const sheetStyle = response.data.data
              for(let i = 0; i < this.sheetOption.series[0].data.length; i++){
                const temp = this.sheetOption.series[0].data[i].name;
                console.log(this.sheetOption.series[0].data[i].value)
                this.sheetOption.series[0].data[i].value = sheetStyle[temp]
                console.log(this.sheetOption.series[0].data[i].value)
              }
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
        // 获取用户性别分布
        this.$axios({
          mathod: 'get',
          url: '/client/sex',
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              const clientSex = response.data.data
              for(let i = 0; i < this.userOption.series[0].data.length; i++){
                const temp = this.userOption.series[0].data[i].name;
                this.userOption.series[0].data[i].value = clientSex[temp]
              }
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
        // 获取歌手国籍分布
        this.$axios({
          mathod: 'get',
          url: '/singer/country',
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              const singerCountry = response.data.data
              for(let i = 0; i < this.singerOption.xAxis.data.length; i++){
                const temp =this.singerOption.xAxis.data[i];
                this.singerOption.series[0].data[i] = singerCountry[temp]
              }
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
      }
      
    },
    mounted(){
      this.draw();
    }
  } 
</script>

<style scoped>
.el-row {
    margin-bottom: 20px;
}
.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}
.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}
.grid-num {
    font-size: 30px;
    font-weight: bold;
}
.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}
.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}
.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}
.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}
.grid-con-2 .grid-num {
    color: rgb(100, 213, 114);
}
.grid-con-3 .grid-con-icon {
    background: rgb(196, 164, 35);
}
.grid-con-3 .grid-num {
    color: rgb(196, 164, 35);
}
.grid-con-4 .grid-con-icon {
    background: rgb(242, 94, 67);
}
.grid-con-4 .grid-num {
    color: rgb(242, 94, 67);
}
.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}
.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}
.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}
.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}
.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}
.user-info-list span {
    margin-left: 70px;
}
.mgb20 {
    margin-bottom: 20px;
}
.todo-item {
    font-size: 14px;
}
.todo-item-del {
    text-decoration: line-through;
    color: #999;
}
.schart {
    width: 100%;
    height: 300px;
}
.cav-info {
  border-radius: 6px;
  overflow: hidden;
  height: 485px;
  background-color: white;
}
.cav-info-2 {
  border-radius: 6px;
  overflow: hidden;
  height: 230px;
  background-color: white;
}
</style>
