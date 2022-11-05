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
        <el-input v-model="query.name" placeholder="歌手名" class="handle-input mr10"></el-input>
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
        <el-table-column type="selection" width="40" align="center"/>
        <el-table-column prop="id" label="ID" width="50" align="center"/>
        <el-table-column label="头像" prop="img" width="110" align="center">//没搞懂
          <template slot-scope="scope">
            <el-image
              class="table-td-thumb"
              :src="scope.row.img"
              :preview-src-list="[scope.row.img]"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="singerName" label="歌手名" width="120" align="center"/>
        <el-table-column prop="sex" label="性别" width="60" align="center"/>
        <el-table-column prop="birthday" label="生日" width="120" align="center"/>
        <el-table-column prop="location" label="地址" width="100" align="center"/>
        <el-table-column label="简介" prop="intro">
          <template v-slot="scope">
            <p style="height: 100px; overflow: scroll">
              {{ scope.row.intro }}
            </p>
          </template>
        </el-table-column>
        <el-table-column label="歌曲管理" width="120" align="center">
          <template v-slot="scope">
            <el-button type="primary" plain @click="goSongPage(scope.row)">歌曲管理</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              class="blue"
              @click="handleEdit(scope.row)"
            >编辑</el-button>
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

    <!-- 添加 -->
    <el-dialog title="添加歌手" :visible.sync="addVisible">
      <el-form label-width="80px" ref="singer" :model="singer">
        <el-form-item label="歌手名" prop="singer_name">
          <el-input v-model="singer.singerName"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="singer.sex">
            <el-radio label="女">女</el-radio>
            <el-radio label="男">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="地址" prop="location">
          <el-input v-model="singer.location"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker type="date" v-model="singer.birthday"></el-date-picker>
        </el-form-item>
        <el-form-item label="歌手介绍" prop="intro">
          <el-input type="textarea" v-model="singer.intro"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveAdd">确 定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible">
      <el-form label-width="60px" ref="form" :model="form">
        <el-form-item label="歌手名" prop="singer_name">
          <el-input v-model="form.singerName"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="女">女</el-radio>
            <el-radio label="男">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生" prop="birthday">
          <el-date-picker type="date" v-model="form.birthday"></el-date-picker>
        </el-form-item>
        <el-form-item label="住址" prop="location">
          <el-input v-model="form.location"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="intro">
          <el-input type="textarea" v-model="form.intro"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveEdit">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  export default {
    name: 'Singer',
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
        addVisible: false,
        editVisible: false,
        singer: {},
        form: {}
      };
    },
    computed:{
      ...mapState(['token'])
    },
    created() {
      this.getData();
    },
    methods: {
      // 获取表格数据 --> 全部信息
      getData() {
        this.$axios({
          method: 'get',
          url: `/singer/${this.query.pageSize}/${this.query.pageIndex}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.singerList
              this.query.pageTotal = response.data.data.pageNum
              console.log("--------")
              console.log(response)
              console.log(this.tableData)
            }else{
              this.$message.error(response.data.message);
            }
          },
          error => {
            this.$message.error(error.message);//跳转404
          }
        )
      },
      // 触发搜索按钮 --> 获取搜索信息 query.name
      handleSearch() {
        this.$set(this.query, 'pageIndex', 1);
        this.$axios({
          method: 'get',
          url: `/singer/search/${this.query.pageSize}/${this.query.pageIndex}/${this.query.name}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.singerList
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

      handleEdit(row) {
        this.editVisible = true;
        this.form = row;
      },
      handleAdd() {
        this.addVisible = true;
      },
      // 保存编辑
      saveEdit() {
        this.$axios({
          method: 'post',
          url: `/singer/`,
          headers: {'token': this.token},
          params:{_method:'put', singer: this.form}
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
        this.editVisible = false;
        this.$message.success(`修改成功`);
        this.$set(this.tableData, this.idx, this.form);
      },
      // 保存新增
      saveAdd() {
        this.$axios({
          method: 'post',
          url: `/singer/`,
          params: this.singer,
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
     // 删除操作
      handleDelete(singer) {//row为该删除对象
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', 
          {type: 'warning'}).then(() => {
            this.$axios({
              method: 'post',
              url: `/singer/${singer.id}`,
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
        let str = '';
        for(var i = 0; i < this.multipleSelection.length; i++){
          str += this.multipleSelection[i].name + ' ';
          this.handleDelete(this.multipleSelection[i])
        }
        this.$message.error(`删除了${str}`);
        this.multipleSelection = [];
      },
      // -------------
      goSongPage(row){
        this.$router.push('/Home/Song');
      },
      //---------------
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