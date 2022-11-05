import Vue from 'vue'
import Router from 'vue-router'

//引入路由组件
import Home from '../pages/Home'
import Login from '../pages/Login'
import Client from '../pages/Client'

Vue.use(Router)

export default new Router({
  routes: [
    {
      name:
      path: '/Home',
      component: Home,
      meta: { title: '主页' },
      children:[
        {
          path: 'Client',
          component: Client,
          meta: { title: '用户管理' }
        }
      ]
    },
    {
      path: '/',
      component: Login,
      meta: { title: '登录' }
    }
  ]
})
