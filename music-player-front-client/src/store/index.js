import Vue from 'vue'
import Vuex from 'vuex'
import mutations from './mutation'

Vue.use(Vuex)

const store = new Vuex.Store({
    state:{
        globalMusicUrl:"",
        globalMusicInfo:{},
        globalCurrentTime:0,
        isMusicPaused:true,
        musicQueue:[],
        nowIndex:0,
        deleteToNext:false,
        queuePos:{},
        queueStyle:'normal',
        isLogin: false,
        uid: '',
        token: '',
        userImg: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
        baseURL: '',
        showBotton: false
    },
    mutations
})

export default store