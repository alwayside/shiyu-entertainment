// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router/router'
import vuetify from './plugins/vuetify'
import './plugins/axios'
import 'vuetify/dist/vuetify.min.css'
import VueClipboard from 'vue-clipboard2'
import i18n from './plugins/i18n'
import store from './plugins/store'
import axios from 'axios'
Vue.prototype.$http = axios

// 全局路由构造函数，判断是否登录和要跳转到页面
router.beforeEach((to, from, next) => {
  let token = window.localStorage.getItem('token')
  if (to.matched.some(record => record.meta.requiresAuth) && (!token || token === null)) {
    next({
      path: '/',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
})

Vue.use(vuetify, {
  lang: {
    t: (key, ...params) => i18n.t(key, params)
  }
})

Vue.config.productionTip = false

VueClipboard.config.autoSetContainer = true
Vue.use(VueClipboard)

new Vue({
  router,
  i18n,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
