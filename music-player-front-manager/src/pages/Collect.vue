<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-button
          type="primary"
          icon="el-icon-delete"
          class="handle-del mr10"
          @click="delAllSelection"
        >批量删除</el-button>
        <el-input v-model="query.songName" placeholder="歌名" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        ref="multipleTable"
        header-cell-class-name="table-header"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40" align="center"></el-table-column>
        <el-table-column prop="name" label="歌手-歌名"/>
        <el-table-column label="操作" width="90" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
    name: 'Collect',
    data() {
      return {
        query: {
          songName: '', //搜索使用
        },
        tableData: [], //总数据
        multipleSelection: [], //筛选的选项
      };
    },
    computed:{
      ...mapState(['token'])
    },
    created() {
      this.getData();
    },
    methods: {
      // 获取表格数据
      getData() {
        this.$axios({
          method: 'get',
          url: `/myListContain/${this.$route.query.clientId}/1`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              const songSinger = response.data.data.songList
              let tempData = []
              for(var i =0; i < songSinger.length; i++){
                const songSingerName = songSinger[i].singer.singerName + '-' + songSinger[i].song.songName
                const songSingerId = songSinger[i].song.id
                tempData.push({
                  id:songSingerId,
                  name:songSingerName
                })
              }
              this.tableData = tempData
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
      },
      // 触发搜索按钮
      handleSearch() {
        if(this.query.songName == null | this.query.songName == ''){
          this.$message.error("输入不能为空!")
          this.query.pageIndex = 1;
          this.getData()
          return
        }
        this.$axios({
          method: 'get',
          url: `/myListContain/search/${this.$route.query.clientId}/1/${this.query.songName}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              const songSinger = response.data.data.songList
              let tempData = []
              for(var i =0; i < songSinger.length; i++){
                const songSingerName = songSinger[i].singer.singerName + '-' + songSinger[i].song.songName
                const songSingerId = songSinger[i].song.id
                tempData.push({
                  id:songSingerId,
                  name:songSingerName
                })
              }
              this.tableData = tempData
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
      },
      // 删除操作
      handleDelete(row) {//row为该删除对象
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', 
          {type: 'warning'}).then(() => {
            this.$axios({
              method: 'post',
              url: `/myListContain/${this.$route.query.clientId}/${row.id}/1`,
              params:{_method: 'delete'},
              headers: {'token': this.token}
            }).then(
              response => {
                if(response.data.type == "success"){
                  this.$message.success('删除成功!');
                  this.getData();
                }else{
                  this.$message.error(response.data.message);
                }
              },
              error => {
                this.$message.error(error.message);//跳转404
              }
            )
          });
      },
      // 多选操作
      handleSelectionChange(val) {//val为选中的对象数组
        this.multipleSelection = val
      },
      //多项删除
      delAllSelection() {
        for(var i = 0; i < this.multipleSelection.length; i++){
          this.$axios({
              method: 'post',
              url: `/myListContain/${this.$route.query.clientId}/${this.multipleSelection[i].id}/1`,
              params:{_method: 'delete'},
              headers: {'token': this.token}
            }).then(
              response => {
                if(response.data.type !== "success"){
                  this.$message.error(response.data.message);
                }
              },
              error => {
                this.$message.error(error.message);//跳转404
              }
            )
        }
        this.$message.success(`删除成功`);
        this.multipleSelection = [];
        this.getData();
      }
    }
  }
</script>

<style scoped>
  .handle-box {
    margin-bottom: 20px;
  }
  .handle-select {
    width: 120px;
  }
  .handle-input {
    width: 300px;
    display: inline-block;
  }
  .table {
    width: 100%;
    font-size: 14px;
  }
  .red {
    color: #ff0000;
  }
  .blue{
    color: #409EFF
  }
  .mr10 {
    margin-right: 10px;
  }
  .table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
  }
</style>
