// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store/index'
import * as echarts from 'echarts'

Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.prototype.$echarts=echarts;
// 设置反向代理，前端请求默认发送到 http://localhost:9999
var axios = require('axios')
axios.default.baseURL = 'http://localhost:9999'
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据

// 路由发生变化修改页面title
router.beforeEach((to, _, next) => {
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
