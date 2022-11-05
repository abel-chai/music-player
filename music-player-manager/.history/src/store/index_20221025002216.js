import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const actions = {}

const mutations = {
  collapseChage(state,value){
    state.isCollapse
  }
}

const state = {
  isCollapse: true
}

export default new Vuex.Store({
  actions,
  mutations,
  state
})