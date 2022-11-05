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
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
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
    <!--添加歌曲-->
    <el-dialog title="添加歌曲" :visible.sync="addVisible">
      <el-form label-width="80px" :model="form">
        <el-form-item prop="singerName" label="歌手名字">
          <el-input v-model="form.singerName"></el-input>
        </el-form-item>
        <el-form-item prop="songName" label="歌曲名字">
          <el-input v-model="form.songName"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveAdd">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
  import { mapState } from 'vuex';
  export {mapState} from 'vuex'

  export default {
    name: 'Content',
    data() {
      return {
        query: {
          songName: '', //搜索使用
        },
        tableData: [], //总数据
        multipleSelection: [], //筛选的选项
        addVisible: false,
        form: {},
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
          url: `/sheetContain/pre/${this.$route.query.sheetId}`,
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
        this.tableData = [{
          name:'张杰-逆战'
        }];
        this.query.pageTotal = 1//
      },
      handleAdd() {
        this.addVisible = true;
      },
      // 保存新增
      saveAdd() {
        this.$axios({
          method: 'post',
          url: `/sheetContain/${this.$route.query.sheetId}/${this.form.singerName}/${this.form.songName}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.addVisible = false;
              this.$message.success(`新增成功`);
              this.getData();
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
      },
      // 触发搜索按钮 ----------------------
      handleSearch() {
        if(this.query.songName == null | this.query.songName == ''){
          this.$message.error("输入不能为空!")
          this.getData()
          return
        }
        this.$axios({
          method: 'get',
          url: `/sheetContain/search/${this.$route.query.sheetId}/${this.query.songName}`,
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
              url: `/sheetContain/${this.$route.query.sheetId}/${row.id}`,
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
              url: `/sheetContain/${this.$route.query.sheetId}/${this.multipleSelection[i].id}`,
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
      },
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