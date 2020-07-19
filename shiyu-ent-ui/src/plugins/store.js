import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    area: null,
    token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
  },
  mutations: {
    setArea (state, area) {
      state.area = area
    },
    $_setToken (state, value) { // 设置存储token
      state.token = value
      localStorage.setItem('token', value)
    },
    $_removeToken (state, value) { // 删除token
      localStorage.removeItem('token')
    }
  },
  actions: {
    changeArea ({ commit }, area) {
      commit('setArea', area)
    }
  },
  getters: { // 监听数据变化的
    getStorage (state) { // 获取本地存储的登录信息
      if (!state.token) {
        state.token = JSON.parse(localStorage.getItem('token'))
      }
      return state.token
    }
  }
})
