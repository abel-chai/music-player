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
        <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
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
        <el-table-column prop="id" label="ID" width="50" align="center"></el-table-column>
        <el-table-column label="头像" prop="img" width="110" align="center">
          <template slot-scope="scope">
            <el-image
              class="table-td-thumb"
              :src="'localhost' + scope.row.img"
              :preview-src-list="[scope.row.img]"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="80" align="center"/>
        <el-table-column prop="sex" label="性别" width="50" align="center"/>
        <el-table-column prop="phone" label="手机号码" width="120" align="center"/>
        <el-table-column prop="email" label="邮箱" width="120" align="center"/>
        <el-table-column prop="birthday" label="生日" width="120" align="center"/>
        <el-table-column prop="location" label="地址" width="70" align="center"/>
        <el-table-column prop="signature" label="签名" align="center"></el-table-column>
        <el-table-column label="收藏" width="90" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-collection"
              class="blue"
              @click="goCollect(scope.row)"
            >收藏</el-button>
          </template>
        </el-table-column>
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
      <div class="pagination">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :current-page="query.pageIndex"
          :page-size="query.pageSize"
          :total="query.pageTotal"
          @current-change="handlePageChange"
        />
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
    name: 'Client',
    data() {
      return {
        query: {
          name: '', //搜索使用
          pageIndex: 1, //当前页码
          pageSize: 6, //每页显示条数
          pageTotal: 0, //总页数
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
          url: `/client/${this.query.pageSize}/${this.query.pageIndex}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              console.log(response.data.data.clientList)
              this.tableData = response.data.data.clientList
              this.query.pageTotal = response.data.data.pageNum
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
        if(this.query.name == null | this.query.name == ''){
          this.$message.error("输入不能为空!")
          this.getData()
          return
        }
        this.$set(this.query, 'pageIndex', 1);
        this.$axios({
          method: 'get',
          url: `/client/search/${this.query.pageSize}/${this.query.pageIndex}/${this.query.name}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.clientList
              this.query.pageTotal = response.data.data.pageNum
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
      handleDelete(client) {//row为该删除对象
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', 
          {type: 'warning'}).then(() => {
            this.$axios({
              method: 'post',
              url: `/client/${client.id}`,
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
              url: `/client/${this.multipleSelection[i].id}`,
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
      //跳转到用户收藏页
      goCollect(client){
        this.$router.push({
          path: '/Home/Collect',
          query: {
            clientId:client.id
          }
        });
      },
      // 分页导航
      handlePageChange(val) {
        this.$set(this.query, 'pageIndex', val);
        this.getData(); //获取指定页码的数据
      }
    }
  };
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
