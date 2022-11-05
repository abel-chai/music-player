import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const actions = {}

const mutations = {
  collapseChange(state,_){
    state.isCollapse = !state.isCollapse
  },
  setAccount(state,account){
    state.account=account
    localStorage.setItem('account',JSON.stringify(account))
  },
  setToken(state,token){
    localStorage.setItem('token',token)
    state.token=token
  },
  logOut(state){
    localStorage.removeItem('token')
    state.token=null
    localStorage.removeItem('account')
    state.account=null
  }
}

const state = {
  isCollapse: false,
  account: localStorage.getItem('account')?localStorage.getItem('account'):null,
	token: localStorage.getItem('token')?localStorage.getItem('token'):null
}

export default new Vuex.Store({
  actions,
  mutations,
  state
})