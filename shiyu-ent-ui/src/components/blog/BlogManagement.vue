<template>
<div>
  <div style="height:120px;"/>
  <v-row ><br /></v-row>
    <v-row>
      <v-col ></v-col>
      <v-col sm="2"><h1>Blog管理</h1></v-col>
      <v-col ></v-col>
    </v-row>
  <v-row>
    <v-col sm=1></v-col>
    <v-col sm=2>
      <div style="min-width:100%;display-flex;flex-direction: column;">
         <v-text-field
            v-model=searchTitle
            label="标题"
          ></v-text-field>
      <v-btn block color="primary" @click=getData()>搜 索</v-btn>
      <v-btn block color="success" class="mt-3"  to="/blog/edit">写一篇Blog</v-btn>
      <v-btn @click="$router.back(-1)" class="mt-3" block color='success' dark >
             返 回
      </v-btn>
      </div>
    </v-col>
    <v-col >
      <div>
<v-card
    class="mx-auto mt-4"
    width="100%"
    elevation="8"
    outlined
    v-for="(item, i) in datas"
    :key=i
  >
    <v-list-item three-line>
      <v-list-item-content>
        <div class="overline mb-4">ARTICLE</div>
        <v-list-item-title class="headline mb-1">{{item.title}}</v-list-item-title>
        <v-list-item-subtitle>
          {{item.desc}}
          </v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-avatar
        tile
        size="80"
        :color="randomRgb()"
      ></v-list-item-avatar>
    </v-list-item>

    <v-card-actions style="display:flex;justify-content:space-between">
      <span style="ml-5 mr-5">作者: {{item.userName}}</span>
          <span style="ml-5 mr-5">更新日期:{{ new Date(Date.parse(item.dateTime)).toLocaleString() }}</span>
      <div v-if="logged"><v-btn text :to=getEditTarget(item.id)>edit</v-btn></div>
      <v-btn text :to=getTarget(item.id)>Detail</v-btn>
    </v-card-actions>
  </v-card>
      </div>
          <v-pagination
          @input="pageChange"
      v-model="page"
      :length=pagelength
      class="ma-7"
    ></v-pagination>
    </v-col>
    <v-col sm=1></v-col>
            </v-row>
               <v-snackbar v-model="errorAlter" color="error">
              {{error}}
              <v-btn dark text @click="errorAlter = false">{{$t('close')}}</v-btn>
            </v-snackbar>
</div>
</template>

<script>

export default {
  name: 'HomePage',
  data: () => ({
    errorAlter: false,
    logged: false,
    pagelength: 1,
    page: 1,
    searchTitle: '',
    datas: []
  }),
  methods: {
    pageChange (e) {
      this.page = e
      this.getData()
    },
    getTarget (id) {
      return 'detail/' + id
    },
    getEditTarget (id) {
      return 'rewrite/' + id
    },
    getData () {
      let url = '/blog/search/management/' + localStorage.getItem('Userid') + '?title=' + this.searchTitle + '&pageNum=' + this.page + '&pageSize=10'
      this.$axios.get(url).then((res) => {
        if (res.status === 200) {
          this.datas = res.data.list
          this.pagelength = res.date.pages
        }
      }).catch((err) => {
        if (err) {
          this.error = err.response.data ? err.response.data : 'error'
          this.errorAlter = true
        }
      })
    },
    randomRgb: function () {
      var R = Math.floor(Math.random() * 255)
      var G = Math.floor(Math.random() * 255)
      var B = Math.floor(Math.random() * 255)
      return 'rgb(' + R + ',' + G + ',' + B + ')'
    }
  },
  computed: {
    area () {
      return this.$i18n.locale
    }
  },
  created () {
  },
  watch: {
    Authorization () {
      let token = localStorage.getItem('Authorization')
      if (token === null || token === '') {
        this.logged = false
      } else {
        this.logged = true
        this.user = window.localStorage.getItem('User')
      }
    }
  },
  mounted: function () {
    this.getData()
    let token = localStorage.getItem('Authorization')
    if (token === null || token === '') {
      this.logged = false
    } else {
      this.logged = true
      this.user = window.localStorage.getItem('User')
    }
  }
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
