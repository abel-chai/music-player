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
        <el-table-column label="图片" prop="img" width="110" align="center">
          <template slot-scope="scope">
            <el-image
              class="table-td-thumb"
              :src="'http://localhost:9999' + scope.row.img"
              :preview-src-list="['http://localhost:9999' + scope.row.img]"
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
          <el-upload
            class="upload-img"
            ref="upload"
            :action="url"
            :data="params"
            :headers="headers"
            :limit="1"
            :on-remove="handleRemove"
            :before-upload="onBeforeUpload"
            :on-success="onSuccess"
            list-type="picture"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg文件,且不超过4MB</div>
          </el-upload>
          <!-- 
            <el-table-column label="评论" width="90" align="center">
              <template v-slot="scope">
                <el-button type="primary" plain @click="goCommentPage(scope.row)">评论</el-button>
              </template>
            </el-table-column>
            <br />
            <el-upload :action="updateSongUrl(scope.row.id)" :show-file-list="false" :on-success="handleSongSuccess" :before-upload="beforeSongUpload" ref="songUpdate">
              <el-button>更新歌曲</el-button>
            </el-upload>
           -->
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
        <el-form-item label="歌曲上传" prop="url">
         <input type="file" name="file" />
        </el-form-item>
        <el-form-item label="歌手id" prop="singerId" v-if="false">
         <el-input v-model="song.singerId"></el-input>
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
      <el-form label-width="80px" ref="form" :model="form">
        <el-form-item 
          label="歌名" 
          prop="songName"
          :rules="[
            {
              required: true,
              validator: (rule, value, callback) => {
                if (value == '' || value == null) {
                  callback('请输入歌名');
                } else {
                  callback();
                }
              },
              trigger: ['blur', 'change']
            }
          ]"
        >
          <el-input v-model="form.songName"></el-input>
        </el-form-item>
        <el-form-item 
          label="专辑" 
          prop="intro"
          :rules="[
            {
              required: true,
              validator: (rule, value, callback) => {
                if (value == '' || value == null) {
                  callback('请输入专辑名');
                } else {
                  callback();
                }
              },
              trigger: ['blur', 'change']
            }
          ]"
        >
          <el-input v-model="form.intro"></el-input>
        </el-form-item>
        <el-form-item 
          label="歌词" 
          prop="lyric"
          :rules="[
            {
              required: true,
              validator: (rule, value, callback) => {
                if (value == '' || value == null) {
                  callback('请输入歌词(或填写暂无歌词)');
                } else {
                  callback();
                }
              },
              trigger: ['blur', 'change']
            }
          ]"
        >
          <el-input type="textarea" v-model="form.lyric"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveEdit('form')">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
  import { mapState } from 'vuex';

  export default {
    name: 'Song',
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
        editVisible: false,
        song: {},
        form: {},
        url: "http://localhost:9999/song/avatar/1",
        params: {_method: 'put'},
        headers: {token: 'this.token}
      };
    },
    computed:{
      ...mapState(['token']),
      // params(){
      //   return {_method: 'put'}
      // },
      // headers(){
      //   return {token: this.token}
      // }
    },
    created() {
      this.getData();
    },
    methods: {
      // 获取表格数据
      getData() {
        this.$axios({
          method: 'get',
          url: `/song/singer/${this.query.pageSize}/${this.query.pageIndex}/${this.$route.query.singerId}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.songList
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
        if(this.query.name == null | this.query.name == ''){
          this.$message.error("输入不能为空!")
          this.getData()
          return
        }
        this.$set(this.query, 'pageIndex', 1);
        this.$axios({
          method: 'get',
          url: `/song/search/${this.query.pageSize}/${this.query.pageIndex}/${this.$route.query.singerId}/${this.query.name}`,
          headers: {'token': this.token}
        }).then(
          response => {
            if(response.data.type == "success"){
              this.tableData = response.data.data.songList
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
      saveEdit(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.$axios({
              method: 'post',
              url: '/song/info',
              headers: {'token': this.token},
              params: {_method: 'put', ...this.form}
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
          } else {
            return false;
          }
        });
      },
      // -------------------- 上传歌曲 传参
      // 保存新增
      saveAdd() {
        this.song.singerId = this.$route.query.singerId
        this.$axios({
          method: 'post',
          url: '/song/',
          params: this.song,
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
      // --------------------
      // 删除操作
      handleDelete(song) {
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', 
          {type: 'warning'}).then(() => {
            this.$axios({
              method: 'post',
              url: `/song/${song.id}`,
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
      // 多项删除
      delAllSelection() {
         for(var i = 0; i < this.multipleSelection.length; i++){
          this.$axios({
              method: 'post',
              url: `/song/${this.multipleSelection[i].id}`,
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
      },
      // -----------------------
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
      // 更新歌词
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
      },
      
      onBeforeUpload(file) {
        const isLt1M = file.size / 1024 / 1024 < 4;

        if (!isLt1M) {
          this.$message.error("上传文件大小不能超过 4MB!");
        }
        return isLt1M;
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      onSuccess(res, file, fileList) {
        console.log(res)
        this.$message.success('图片上传成功');
      },
      handleExceed() {
        this.$message.warning(`当前限制选择 1 个文件`);
        this.getData();
      },
      beforeRemove(file) {
        return this.$confirm(`确定移除 ${ file.name }?`);
      }
      // ------------------------------
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
