import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const actions = {}

const mutations = {
  collapseChange(state,_){
    state.isCollapse = !state.isCollapse
  }
}

const state = {
  isCollapse: false,
  account:localStorage.getItem('account')?localStorage.getItem('account'):null,
		token:localStorage.getItem('token')?localStorage.getItem('token'):null,
————————————————
版权声明：本文为CSDN博主「程序猿小嗨」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/ltp0317/article/details/124940883
}

export default new Vuex.Store({
  actions,
  mutations,
  state
})