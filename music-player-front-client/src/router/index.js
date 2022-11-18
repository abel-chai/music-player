import Vue from 'vue'
import VueRouter from "vue-router"
import store from "../store/index"

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
    meta: { keepAlive: true },
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next() : next('/login')
    }
  },
  {
    path: "/recommend",
    component: () => import ('@/views/Recommend/Recommend.vue'),
    meta: { keepAlive: true, },
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next() : next('/login')
    }
  },
  {
    path: '/result',
    component: () => import ('@/views/Result/Result.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next() : next('/login')
    }
  },
  {
    path: '/playlist',
    component: () => import ('@/views/Playlist/Playlist.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next() : next('/login')
    }
  },
  {
    path: '/myplaylist',
    component: () => import ('@/views/MyPlaylist/MyPlaylist.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next() : next('/login')
    }
  },
  {
    path: '/artist',
    component: () => import ('@/views/Artist/Artist.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next() : next('/login')
    }
  },
  {
    name: 'Login',
    path: '/login',
    component: () => import ('@/views/Login/Login.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next('/user') : next()
    }
  },
  {
    path: '/register',
    component: () => import ('@/views/Register/Register.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next('/user') : next()
    }
  },
  {
    path: '/singer',
    component: () => import ('@/views/FindSinger/FindSinger.vue'),
    beforeEnter: (to, from, next) => {
      const isLogin = store.state.isLogin
      isLogin ? next() : next('/login')
    }
  },
  {
    path: '/user',
    component: () => import ('@/views/User/User.vue'),
    beforeEnter: (to, from, next) => {
      store.state.isLogin ? next() : next({name: 'Login'})
    }
  }
]

const router = new VueRouter({
  routes,
  // mode: 'history'
})

export default router