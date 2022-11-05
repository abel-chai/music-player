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
        <el-input v-model="query.name" placeholder="歌名" class="handle-input mr10"></el-input>
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
        <el-table-column label="图片" prop="img" width="110" align="center">//没搞懂
          <template slot-scope="scope">
            <el-image
              class="table-td-thumb"
              :src="scope.row.thumb"
              :preview-src-list="[scope.row.thumb]"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="songName" label="歌名" width="150" align="center"/>
        <el-table-column prop="intro" label="专辑" width="150" align="center"/>
        <el-table-column prop="lyric" label="歌词" align="center">
          <template v-slot="scope">
            <ul style="height: 100px; overflow: scroll">
              <li v-for="(item, index) in parseLyric(scope.row.lyric)" :key="index">
                {{ item }}
              </li>
            </ul>
          </template>
        </el-table-column>
        <el-table-column label="资源更新" width="120" align="center">
          <!-- <template v-slot="scope">
            <el-upload :action="updateSongImg(scope.row.id)" :show-file-list="false" :on-success="handleImgSuccess" :before-upload="beforeImgUpload" ref="imgUpdate">
              <el-button>更新图片</el-button>
            </el-upload>
            <br />
            <el-upload :action="updateSongUrl(scope.row.id)" :show-file-list="false" :on-success="handleSongSuccess" :before-upload="beforeSongUpload" ref="songUpdate">
              <el-button>更新歌曲</el-button>
            </el-upload>
          </template> -->
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
    <el-dialog title="添加歌曲" :visible.sync="addVisible">
      <el-form label-width="80px" ref="song" :model="song">
        <el-form-item label="歌名" prop="songName">
          <el-input v-model="song.songName"></el-input>
        </el-form-item>
        <el-form-item label="专辑" prop="intro">
          <el-input v-model="song.intro"></el-input>
        </el-form-item>
        <el-form-item label="歌词" prop="lyric">
          <el-input type="textarea" v-model="song.lyric"></el-input>
        </el-form-item>
        <el-form-item label="歌曲上传">
         <input type="file" name="file" />
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
        <el-form-item label="歌名" prop="name">
          <el-input v-model="form.songName"></el-input>
        </el-form-item>
        <el-form-item label="专辑" prop="intro">
          <el-input v-model="form.intro"></el-input>
        </el-form-item>
        <el-form-item label="歌词" prop="lyric">
          <el-input type="textarea" v-model="form.lyric"></el-input>
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
import { mapState } from 'vuex';
  export {mapState} from 'vuex'

  export default {
    name: 'Song',
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
        editVisible: false,
        song: {},
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
          url: `/song/singer/${this.query.pageSize}/${this.query.pageIndex}/${this.$route.query.singerId}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.songList
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
      // 触发搜索按钮 --> 获取搜索信息 query.name
      handleSearch() {
        this.$set(this.query, 'pageIndex', 1);
        this.$axios({
          method: 'get',
          url: `/song/search/${this.query.pageSize}/${this.query.pageIndex}/${this.$route.query.singerId}/${this.query.name}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.songList
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
        this.editVisible = false;
        this.$message.success(`修改成功`);
        // this.$set(this.tableData, this.idx, this.form);
      },
      // 保存新增
      saveAdd() {
        this.addVisible = false;
        this.$message.success(`新增成功`);
        //this.$set(this.tableData, this.idx, this.form);//发送更新请求
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
      },
      updateSongImg(id){
        //
        return '';
      },
      updateSongUrl(id){
        return '';
      },
      handleImgSuccess(){
        this.$refs.imgUpdate.validate(valid => {
          if (valid) {
            this.$message.success('上传成功');
            this.$router.push('/Home/Song');
          } else {
            this.$message.error('上传失败');
            return false;
          }
        });
      },
      handleSongSuccess(){
        this.$refs.songUpdate.validate(valid => {
          if (valid) {
            this.$message.success('上传成功');
            this.$router.push('/Home/Song');
          } else {
            this.$message.error('上传失败');
            return false;
          }
        });
      },
      //更新歌词
      parseLyric(text) {
        const lines = text.split("\n");
        const pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
        const result = [];

        // 对于歌词格式不对的特殊处理
        if (!/\[.+\]/.test(text)) {
          return [text];
        }
        for (const item of lines) {
          if (pattern.test(item)) {
            const value = item.replace(pattern, ""); // 存歌词
            result.push(value);
          }
        }
       return result;
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