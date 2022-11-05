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
        <el-table-column prop="name" label="用户名" width="100" align="center"/>
        <el-table-column prop="password" label="密码" width="180" align="center"/>
        <el-table-column prop="sex" label="性别" width="50" align="center"/>
        <el-table-column prop="phone" label="手机号码" align="center"/>
        <el-table-column prop="email" label="邮箱" width="180" align="center"/>
        <el-table-column prop="birthday" label="生日" width="120" align="center"/>
        <el-table-column prop="location" label="地址" width="100" align="center"/>
        <el-table-column label="操作" width="90" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDelete(scope.$index, scope.row)"
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

    <el-dialog title="新增" :visible.sync="addVisible" width="30%">
      <el-form ref="admin" :model="admin" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="admin.name"></el-input>
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
        <el-form-item label="手机号码">
          <el-input v-model="admin.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="admin.email"></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" v-model="admin.birthday"></el-date-picker>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="admin.phone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAdd">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'Admin',
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
        delList: [],//删除的对象数组
        addVisible: false,
        admin: {},
      };
    },
    created() {
      this.getData();
    },
    methods: {
      // 获取表格数据 --> 全部信息
      getData() {
        this.tableData = [{
          id:'1', name:'abel', sex:'男', phone:'123456', email:'123', birthday:'2002-02-21', password:'123', location:'河南'
        }];
        this.query.pageTotal = 1
      },
      // 触发搜索按钮 --> 获取搜索信息 query.name
      handleSearch() {
        this.$set(this.query, 'pageIndex', 1);
        this.getData();
      },
      handleAdd() {
        this.addVisible = true;
      },
      // 保存新增
      saveAdd() {
        this.addVisible = false;
        this.$message.success(`新增成功`);
        this.$set(this.tableData, this.idx, this.form);//发送更新请求
      },
      // 删除操作
      handleDelete(index, row) {//index为列表索引,row为该删除对象
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', 
          {type: 'warning'}).then(() => {
            this.$message.success('删除成功!');
            this.tableData.splice(index, 1);
          }).catch(() => {
        });
      },
      // 多选操作
      handleSelectionChange(val) {//val为选中的对象数组
        this.multipleSelection = val
      },
      //多项删除
      delAllSelection() {
        const length = this.multipleSelection.length;
        let str = '';
        this.delList = this.delList.concat(this.multipleSelection);
        for (let i = 0; i < length; i++) {
          str += this.multipleSelection[i].name + ' ';
        }
        this.$message.error(`删除了${str}`);
        this.multipleSelection = [];
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