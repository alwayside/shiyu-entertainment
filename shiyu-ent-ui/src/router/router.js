import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Home from '@/components/home/home'
import Blog from '@/components/blog/Blog'
import BlogEdit from '@/components/blog/Edit'
import BlogDetail from '@/components/blog/Detail'
import BlogRewrite from '@/components/blog/rewrite'
import Login from '@/components/login/login'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: Hello
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/blog',
      name: 'Blog',
      component: Blog
    },
    {
      path: '/blog/edit',
      name: 'Blog-edit',
      component: BlogEdit,
      beforeEnter: (to, from, next) => {
        let token = localStorage.getItem('Authorization')
        if (token === null || token === '') {
          next('/login')
        } else {
          next()
        }
      }
    },
    {
      path: '/blog/detail/:id',
      name: 'Blog-detail',
      component: BlogDetail
    },
    {
      path: '/blog/rewrite/:id',
      name: 'Blog-rewrite',
      component: BlogRewrite,
      beforeEnter: (to, from, next) => {
        let token = localStorage.getItem('Authorization')
        if (token === null || token === '') {
          next('/login')
        } else {
          next()
        }
      }
    },
    {
      path: '/login',
      name: 'HelloWord',
      component: Login
    }
  ]
})

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  } else {
    let token = localStorage.getItem('Authorization')
    if (token === 'null' || token === '') {
      next('/login')
    } else {
      next()
    }
  }
})

export default router
