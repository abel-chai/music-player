<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1">
            <i class="el-icon-lx-people grid-con-icon"></i>
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
            <i class="el-icon-lx-notice grid-con-icon"></i>
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
            <i class="el-icon-lx-goods grid-con-icon"></i>
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
            <i class="el-icon-lx-goods grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">5000</div>
              <div>歌单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{ padding: '0px' }">
          <div class="card-content">
            <div class="card-left">
               <i class="el-icon-user grid-con-icon"></i>
            </div>
            <div class="card-right">
              <div class="card-num">{{ userCount }}</div>
              <div>用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{ padding: '0px' }">
          <div class="card-content">
            <div class="card-left">
              <i class="el-icon-headset grid-con-icon"></i>
            </div>
            <div class="card-right">
              <div class="card-num">{{ songCount }}</div>
              <div>歌曲总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{ padding: '0px' }">
          <div class="card-content">
            <div class="card-left">
              <i class="el-icon-service grid-con-icon"></i>
            </div>
            <div class="card-right">
              <div class="card-num">{{ singerCount }}</div>
              <div>歌手数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{ padding: '0px' }">
          <div class="card-content">
            <div class="card-left">
              <i class="el-icon-document grid-con-icon"></i>
            </div>
            <div class="card-right">
              <div class="card-num">{{ sheetCount }}</div>
              <div>歌单数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- <el-row :gutter="20">
      <el-col :span="12">
        <h3>用户性别比例</h3>
        <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="userSex"></el-card>
      </el-col>
      <el-col :span="12">
        <h3>歌曲类型</h3>
        <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="songStyle"></el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <h3>歌手性别比例</h3>
        <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="singerSex"></el-card>
      </el-col>
      <el-col :span="12">
        <h3>歌手国籍</h3>
        <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="country"></el-card>
      </el-col>
    </el-row> -->
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
        sheetCount: 20
      }
    },
    methods:{

    }
  } 
</script>

<style scoped>
.card-content {
  display: flex;
  align-items: center;
  justify-content: space-around;
  height: 100px;
  padding-left: 20%;
  text-align: center;
}

.card-left {
  display: flex;
  font-size: 3rem;
}

.card-right {
  flex: 1;
  font-size: 14px;
}

.card-num {
  font-size: 30px;
  font-weight: bold;
}

h3 {
  margin: 10px 0;
  text-align: center;
}
.cav-info {
  border-radius: 6px;
  overflow: hidden;
  height: 250px;
  background-color: white;
}
</style>
