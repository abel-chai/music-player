import Vue from 'vue'
import Router from 'vue-router'

//引入路由组件
import Home from '../pages/Home'
import Login from '../pages/Login'
import Info from '../pages/Info'
import Client from '../pages/Client'
import Admin from '../pages/Admin'
import Singer from '../pages/Singer'
import Sheet from '../pages/Sheet'
import Collect from '../pages/Collect'
import Song from '../pages/Song'
import Content from '../pages/Content'
import Comment from '../pages/Comment'

Vue.use(Router)

 const router = new Router({
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
          meta: { title: '用户管理' },
        },
        {
          path: 'Admin',
          component: Admin,
          meta: { title: '管理员管理' }
        },
        {
          path: 'Singer',
          component: Singer,
          meta: { title: '歌手管理' }
        },
        {
          path: 'Sheet',
          component: Sheet,
          meta: { title: '歌单管理' }
        },
        {
          path: 'Collect',
          component: Collect,
          meta: { title: '用户收藏' }
        },
        {
          path: 'Song',
          component: Song,
          meta: { title: '歌曲管理' }
        }
        ,
        {
          path: 'Content',
          component: Content,
          meta: { title: '歌单内容' }
        }
        ,
        {
          path: 'Comment',
          component: Comment,
          meta: { title: '歌单评论' }
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


export default router;
