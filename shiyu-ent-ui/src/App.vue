<template>
  <v-app
    style="min-height: calc(100vh - 120px)!important;margin-top: 0!important;background-color:transparent;overflow:hidden"
  >
    <v-content class="pt-0" style>
      <router-view style />
    </v-content>
    <toolbar :style="style"></toolbar>
    <footers :style="style"></footers>
  </v-app>
</template>

<script>
import Toolbar from '@/components/public/Toolbar'
import Footers from '@/components/public/Footers'

export default {
  name: 'App',
  components: {
    Toolbar,
    Footers
  },
  data () {
    return {
      dark: false,
      style: 'none'
    }
  },
  mounted () {
    const curPath = this.$route.path
    if (curPath === '/' || curPath === '') {
      this.style = 'display:none!important'
    } else {
      this.style = ''
    }
  },
  created () {
    window.getApp = this
    // window.localStorage.setItem('bgPic', bgPic)
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        console.log(val)
        console.log(val.path)
        if (val.path === '/' || val.path === '') {
          this.style = 'display:none!important'
        } else {
          this.style = ''
        }
      },
      // 深度观察监听
      deep: true
    }
  }
}
</script>

<style>
@import "style/global.css";

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
