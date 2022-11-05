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
        <el-table-column prop="id" label="ID" width="50" align="center"></el-table-column>
        <el-table-column prop="username" label="用户名" width="150" align="center"/>
        <el-table-column prop="sex" label="性别" width="60" align="center"/>
        <el-table-column prop="phone" label="手机号码" align="center"/>
        <el-table-column prop="email" label="邮箱" width="200" align="center"/>
        <el-table-column prop="birthday" label="生日" width="120" align="center"/>
        <el-table-column prop="location" label="地址" width="150" align="center"/>
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
          :total="query.total"
          @current-change="handlePageChange"
        />
      </div>
    </div>

    <el-dialog title="新增" :visible.sync="addVisible">
      <el-form ref="admin" :model="admin" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="admin.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="admin.password"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="admin.sex">
            <el-radio label="女">女</el-radio>
            <el-radio label="男">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item 
          label="手机号码"
          prop="phone"
          :rules="[{}]"
        >
          <el-input v-model="admin.phone"></el-input> <!-- 校验 -->
        </el-form-item>
        <el-form-item
          prop="email"
          label="邮箱"
          :rules="[
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ]"
        >
          <el-input v-model="admin.email"></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" v-model="admin.birthday"></el-date-picker>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="admin.location"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAdd('admin')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
    name: 'Admin',
    data() {
      return {
        query: {
          name: '', //搜索使用
          pageIndex: 1, //当前页码
          pageSize: 6, //每页显示条数
          total: 0, //总页数
        },
        tableData: [], //总数据
        multipleSelection: [], //筛选的选项
        addVisible: false,
        admin: {},
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
          url: `/admin/${this.query.pageSize}/${this.query.pageIndex}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.adminList
              this.query.total = response.data.data.totalNum
            }else{
              // if(response.data.message == "认证失败"){
              //   this.$router.push("/")
              // }
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
          url: `/admin/search/${this.query.pageSize}/${this.query.pageIndex}/${this.query.name}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.adminList
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
      // 新增
      handleAdd() {
        this.addVisible = true;
      },
      // 保存新增
      saveAdd(admin) {
        this.$refs[admin].validate((valid) => {
          if (valid) {
            this.$axios({
              method: 'post',
              url: '/admin/',
              params: this.admin,
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
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      // 删除操作
      handleDelete(admin) {//admin为该删除对象
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', 
          {type: 'warning'}).then(() => {
            this.$axios({
              method: 'post',
              url: `/admin/${admin.id}`,
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
              url: `/admin/${this.multipleSelection[i].id}`,
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
