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
        <el-input v-model="query.title" placeholder="标题" class="handle-input mr10"></el-input>
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
        <el-table-column label="图片" prop="img" width="110" align="center">
          <template slot-scope="scope">
            <el-image
              class="table-td-thumb"
              :src="'http://localhost:9999' + scope.row.img"
              :preview-src-list="['http://localhost:9999' + scope.row.img]"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" width="200" align="center"/>
        <el-table-column prop="style" label="风格" width="100" align="center"/>
        <el-table-column label="简介" prop="intro" align="center">
          <template v-slot="scope">
            <p style="height: 100px; overflow: scroll">
              {{ scope.row.intro }}
            </p>
          </template>
        </el-table-column>
        <el-table-column label="内容" width="90" align="center">
          <template v-slot="scope">
            <el-button type="primary" plain @click="goContentPage(scope.row)">内容</el-button>
          </template>
        </el-table-column>
        <el-table-column label="评论" width="90" align="center">
          <template v-slot="scope">
            <el-button type="primary" plain @click="goCommentPage(scope.row)">评论</el-button>
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
          :total="query.total"
          @current-change="handlePageChange"
        />
      </div>
    </div>

    <!-- 添加 -->
    <el-dialog title="添加歌单" :visible.sync="addVisible">
      <el-form label-width="80px" ref="sheet" :model="sheet">
        <el-form-item label="标题" prop="title">
          <el-input v-model="sheet.title"></el-input>
        </el-form-item>
         <el-form-item label="风格" prop="style">
          <el-input v-model="sheet.style"></el-input>
        </el-form-item>
        <el-form-item label="歌单介绍" prop="intro">
          <el-input v-model="sheet.intro"></el-input>
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
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="风格" prop="style">
          <el-input v-model="form.style"></el-input>
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
    name: 'Sheet',
    data() {
      return {
        query: {
          title: '', //搜索使用
          pageIndex: 1, //当前页码
          pageSize: 4, //每页显示条数
          total: 0, //总页数
        },
        tableData: [], //总数据
        multipleSelection: [], //筛选的选项
        addVisible: false,
        editVisible: false,
        sheet: {},
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
      // 获取表格数据
      getData() {
        this.$axios({
          method: 'get',
          url: `/sheet/${this.query.pageSize}/${this.query.pageIndex}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.sheetList
              this.query.total = response.data.data.totalNum
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
        if(this.query.title == null | this.query.title == ''){
          this.$message.error("输入不能为空!")
          this.getData()
          return
        }
        this.$set(this.query, 'pageIndex', 1);
        this.$axios({
          method: 'get',
          url: `/sheet/search/${this.query.pageSize}/${this.query.pageIndex}/${this.query.title}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.sheetList
              this.query.total = response.data.data.totalNum
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
          url: '/sheet/info',
          headers: {'token': this.token},
          params:{_method: 'put', id: this.form.id, title: this.form.title, style: this.form.style, intro: this.form.intro}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.editVisible = false;
              this.$message.success(`修改成功`);
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
      // 保存新增
      saveAdd() {
        this.$axios({
          method: 'post',
          url: '/sheet/',
          params: this.sheet,
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
      handleDelete(sheet) {
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', 
          {type: 'warning'}).then(() => {
            this.$axios({
              method: 'post',
              url: `/sheet/${sheet.id}`,
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
              url: `/sheet/${this.multipleSelection[i].id}`,
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
      //歌单内歌曲列表
      goContentPage(sheet){
        this.$router.push({
          path: '/Home/Content',
          query: {
            sheetId:sheet.id
          }
        });
      },
      //歌单评论
      goCommentPage(sheet){
        this.$router.push({
          path: '/Home/Comment',
          query: {
            sheetId:sheet.id
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
