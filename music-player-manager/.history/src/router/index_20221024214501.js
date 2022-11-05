import Vue from 'vue'
import Router from 'vue-router'

//引入路由组件
import Home from '../pages/Home'
import Login from '../pages/Login'
import Info from '../pages/Info'
import Client from '../pages/Client'
import Admin from '../pages/Admin'
import 

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Home',
      component: Home,
      meta: { title: '主页' },
      children:[
        {
          path: 'Info',
          component: Info,
          meta: {title: '详情'}
        },
        {
          path: 'Client',
          component: Client,
          meta: { title: '用户管理' }
        },
        {
          path: 'Admin',
          component: Admin,
          meta: { title: '管理员管理' }
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
