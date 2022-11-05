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
}

export default new Vuex.Store({
  actions,
  mutations,
  state
})