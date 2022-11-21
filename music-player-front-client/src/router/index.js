import Vue from 'vue'
import VueRouter from "vue-router"
import {userInfoAPI} from '../utils/api.js'
import store from '../store/index'
import {Message} from 'element-ui'

Vue.use(VueRouter)

// 解决重复点击导航时，控制台出现报错
const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

const routes = [
  {
    path: "/",
    redirect: '/discover'
  },
  {
    path: "/discover",
    component: () => import ('@/views/Discover/Discover.vue'),
    meta: { keepAlive: true }
  },
  {
    path: "/blank",
    component: () => import ('@/views/Blank/Blank.vue')
  },
  {
    path: "/recommend",
    component: () => import ('@/views/Recommend/Recommend.vue'),
    meta: { keepAlive: true, }
  },
  {
    path: '/result',
    component: () => import ('@/views/Result/Result.vue')
  },
  {
    path: '/playlist',
    component: () => import ('@/views/Playlist/Playlist.vue')
  },
  {
    path: '/myplaylist',
    component: () => import ('@/views/MyPlaylist/MyPlaylist.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = localStorage.getItem('isLogin')
      isLogin ? next() : next('/login')
    }
  },
  {
    path: '/collection',
    component: () => import ('@/views/Collection/Collection.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = localStorage.getItem('isLogin')
      isLogin ? next() : next('/login')
    }
  },
  {
    path: '/artist',
    component: () => import ('@/views/Artist/Artist.vue')
  },
  {
    name: 'Login',
    path: '/login',
    component: () => import ('@/views/Login/Login.vue'),
    beforeEnter: (to, from, next) => {
      const { isLogin } = localStorage
      if(isLogin == 'true') next('/user')
      else next()
    }
  },
  {
    path: '/register',
    component: () => import ('@/views/Register/Register.vue'),
    beforeEnter: (to, from, next) => {
      const { isLogin } = localStorage
      if(isLogin == 'true') next('/user')
      else next()
    }
  },
  {
    path: '/singer',
    component: () => import ('@/views/FindSinger/FindSinger.vue')
  },
  {
    path: '/user',
    component: () => import ('@/views/User/User.vue'),
    beforeEnter: (to, from, next) => {
      const { isLogin } = localStorage;
      if(isLogin === 'true') {
        next() 
      }
      else {
        Message({
          type: 'info',
          message: '请先登录'
        });   
        next({name: 'Login'})
      }
    }
  }
]

const router = new VueRouter({
  routes,
  // mode: 'history'
})

router.beforeEach((to, from, next) => {
  const { isLogin, uid } = localStorage;
  console.log(uid);
  if(isLogin === 'true') {
    userInfoAPI(uid).then(res=>{
      store.state.userImg = store.state.baseURL+res.data.data.img
    })
  }
  else {
    store.state.userImg='https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'
  }
  next()
})

export default router