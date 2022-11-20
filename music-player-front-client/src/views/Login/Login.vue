<template>
  <div class="box">
    <div class="top">
      <img src="https://p5ui.morna.cn/favicon.ico" alt="" class="icon">
      <!-- <span class="title">&nbsp;&nbsp;登录</span> -->
    </div>
    <div class="input">
      <el-input placeholder="请输入账号" v-model="uname"></el-input>
      <div style="margin-bottom: 30px;"></div>
      <el-input placeholder="请输入密码" v-model="pword" show-password></el-input>
    </div>
    <div class="button">
      <el-button type="danger" plain @click="commit">登录</el-button>
    </div>
    <div style="text-align: center;margin-top: 20px;">
      <router-link to="/register">没有账号？去注册</router-link></div>
  </div>
</template>

<script>
import { loginAPI } from '@/utils/api'

export default {
  name: 'Login',
  data() {
    return {
      uname: '',
      pword: '',
      type: ""
    }
  },
  methods: {
    commit() {
      loginAPI({name: this.uname, password: this.pword}).then(res=>{
        if(res.data.type == "success") {
          localStorage.isLogin = true
          localStorage.token = this.$store.state.token
          this.$router.push('/user')
          this.$message({
              showClose: true,
              message: '登录成功',
              type: 'success'
          });
        }else {
          this.$message({
              showClose: true,
              message: '登录失败',
              type: 'error'
          });
          return
        }
        this.$store.state.token = res.data.data.token
        this.type = res.data.type
        localStorage.uid = res.data.data.id
        
      })
      
    }
  }
}
</script>

<style scoped>
.box {
  margin: 50px 150px 75px 200px;
  width: 800px;
  height: 500px;
  border-radius: 30px;
  border-top: 1px solid #e8e8e8;
  background: url(../../assets/imgs/moka.png) no-repeat;
  background-size: 40%;
  background-position: bottom right;
  box-shadow: 0 5px 6px rgba(0, 0, 0,.3);
}
.top{
  width: 100px;
  margin: 50px auto;
}
.icon {
  width: 80px;
  height: 80px;
}
.input {
  margin: 50px 150px;
  width: 400px;
}
.text {
  font-size: 16px;
  height: 42px;
}
.button {
  width: 100px;
  margin: auto;
}
.el-button {
  font-size: 20px;
}
a {
  color: red;
}
</style>