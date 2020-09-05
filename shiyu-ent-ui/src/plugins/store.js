import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    area: null,
    Userid: localStorage.getItem('Userid') ? localStorage.getItem('User') : '',
    User: localStorage.getItem('User') ? localStorage.getItem('User') : '',
    Username: localStorage.getItem('Username') ? localStorage.getItem('Username') : '',
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : ''
  },
  mutations: {
    setArea (state, area) {
      state.area = area
    },
    setToken (state, value) {
      state.Authorization = value.token
      localStorage.setItem('Authorization', value.token)
    },
    removeToken (state, value) { // 删除token
      state.User = null
      state.Username = null
      state.Userid = null
      state.Authorization = null
      localStorage.removeItem('Authorization')
      localStorage.removeItem('User')
      localStorage.removeItem('Username')
      localStorage.removeItem('Userid')
    },
    setUser (state, value) {
      state.User = value.account
      state.Username = value.userName
      state.Userid = value.id
      localStorage.setItem('User', value.account)
      localStorage.setItem('Username', value.userName)
      localStorage.setItem('Userid', value.id)
    },
    removeUser () { // 删除User
      localStorage.removeItem('User')
      localStorage.removeItem('Username')
      localStorage.removeItem('Userid')
    }
  },
  actions: {
    changeArea ({ commit }, area) {
      commit('setArea', area)
    },
    login ({ commit }, data) {
      commit('setToken', data)
      commit('setUser', data)
    },
    logout ({ commit }, data) {
      commit('removeToken', data)
    }
  },
  getters: { // 监听数据变化的
    getStorage (state) { // 获取本地存储的登录信息
      // if (!state.token) {
      //   state.token = JSON.parse(localStorage.getItem('token'))
      // }
      // return state.token
    }
  }
})
