import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Home from '@/components/home/home'
import Blog from '@/components/blog/Blog'
import BlogEdit from '@/components/blog/Edit'
import BlogDetail from '@/components/blog/Detail'
import BlogRewrite from '@/components/blog/rewrite'

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
      name: 'HelloWorld',
      component: Home
    },
    {
      path: '/blog',
      name: 'HelloWorld',
      component: Blog
    },
    {
      path: '/blog/edit',
      name: 'HelloWorld',
      component: BlogEdit
    },
    {
      path: '/blog/detail/:id',
      name: 'HelloWorld',
      component: BlogDetail
    },
    {
      path: '/blog/rewrite/:id',
      name: 'HelloWorld',
      component: BlogRewrite
    }
  ]
})

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
