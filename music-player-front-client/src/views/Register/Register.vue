<template>
  <div class="box">
    <div class="top">
      <img src="https://p5ui.morna.cn/favicon.ico" alt="" class="icon">
      <!-- <span class="title">&nbsp;&nbsp;登录</span> -->
    </div>
    <div class="input">
      <el-input placeholder="请输入账号" v-model="putData.name"></el-input>
      <div style="margin-bottom: 30px;"></div>
      <el-input placeholder="请输入密码" v-model="putData.password" show-password></el-input>
    </div>
    <div class="button">
      <el-button type="danger" plain @click="commit">注册</el-button>
    </div>
    <div style="text-align: center;margin-top: 20px;">
      <router-link to="/login">想起账号了？去登录</router-link></div>
  </div>
</template>

<script>
import { registerAPI } from '@/utils/api'

export default {
  name: 'Register',
  data() {
    return {
      putData: {
        name: '',
        password: ''  
      }
    }
  },
  methods: {
    commit() {
      if(this.putData.name === '' || this.putData.password === '') {
        alert("格式错误")
      }
      else registerAPI(this.putData)
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
  background-size: 30%;
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
  margin: 50px auto;
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