<template>
  <div class="box">
    <div class="top">
      <img src="https://p5ui.morna.cn/favicon.ico" alt="" class="icon">
      <!-- <span class="title">&nbsp;&nbsp;登录</span> -->
    </div>
    <div class="input">
      <el-input placeholder="请输入3~16位的用户名，以字母开头" v-model="putData.username"></el-input>
      <div style="margin-bottom: 30px;"></div>
      <el-input placeholder="请输入6~16位密码，不能包含特殊字符" v-model="putData.password" show-password></el-input>
    </div>
    <div class="button">
      <el-button type="danger" plain @click="commit">注册</el-button>
    </div>
    <div style="text-align: center;margin-top: 20px;">
      <router-link to="/login">想起账号了？去登录</router-link></div>
  </div>
</template>

<script>
import { registerAPI,loginAPI } from '@/utils/api'

export default {
  name: 'Register',
  data() {
    return {
      putData: {
        username: '',
        password: ''  
      }
    }
  },
  methods: {
    commit() {
      const reg1 = /^[a-zA-Z][a-zA-Z0-9_]{3,15}$/;
      const reg2 = /\w{5,17}$/
      if(this.putData.username === '' || this.putData.password === '') {
        this.$message({
          type:'info',
          message:'输入不能为空',
          showClose:true
        })
        return
      }
      if(!reg1.test(this.putData.username)) {
        this.$message({
              showClose: true,
              message: '请输入合法的账号',
              type: 'info'
          });
          return
      }
      if(!reg2.test(this.putData.password)) {
        this.$message({
              showClose: true,
              message: '请输入合法的密码',
              type: 'info'
          });
          return
      }
      registerAPI(this.putData).then(()=>{
        this.$message({
          type:'success',
          message:'注册成功',
          showClose:true
        })
        loginAPI({name: this.putData.username, password: this.putData.password}).then(res=>{
        this.$store.state.token = res.data.data.token
        this.type = res.data.type
        localStorage.uid = res.data.data.id
      }).then(() => {
        if(this.type == "success") {
          localStorage.isLogin = true
          localStorage.token = this.$store.state.token
        }
        this.$router.push('/user')
      })
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