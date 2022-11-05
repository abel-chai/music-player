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
          <div class="grid-content grid-con-1">
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
          <div class="grid-content grid-con-3">
            <i class="el-icon-document grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{sheetCount}}</div>
              <div>歌单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- <el-row>
      <el-col align="center">
        <h2>歌曲类型</h2>
        <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="songVar"></el-card>
      </el-col>
    </el-row> -->
    <el-row :gutter="20">
      <el-col :span="12" align="center">
        <h3>用户性别比例</h3>
        <el-card class="cav-info-2" shadow="hover" :body-style="{ padding: '0px' }" id="userVar"></el-card> 
        <h3>歌手国籍</h3>
        <el-card class="cav-info-2" shadow="hover" :body-style="{ padding: '0px' }" id="singerVar"></el-card> 
      </el-col>
      <el-col :span="12" align="center"> 
        <h3>歌单类型</h3>
        <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="songVar"></el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default{
    name:'info',
    data(){
      return{
        userCount: 10,
        songCount: 100,
        singerCount: 30,
        sheetCount: 20,
        songOption: {
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
                { value: 1048, name: '华语' },
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
    methods:{
      draw(){
        const userVar = this.$echarts.init(document.getElementById("userVar"));
        userVar.setOption(this.userOption);

        const songVar = this.$echarts.init(document.getElementById("songVar"));
        songVar.setOption(this.songOption);

        const singerVar = this.$echarts.init(document.getElementById("singerVar"));
        singerVar.setOption(this.singerOption);
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
.grid-con-3 .grid-num {
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
