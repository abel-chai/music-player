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
  isCollapse: false
}

export default new Vuex.Store({
  actions,
  mutations,
  state
})