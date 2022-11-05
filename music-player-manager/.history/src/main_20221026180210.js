// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store/index'
import * as echarts from 'echarts'
import axios from 'axios'

Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.prototype.$echarts=echarts;

// 设置反向代理，前端请求默认发送到 http://localhost:7080/api
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:7080/api'
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据

————————————————
版权声明：本文为CSDN博主「在奋斗的大道」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/zhouzhiwengang/article/details/103797848

router.beforeEach((to, _, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
