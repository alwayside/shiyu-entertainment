<template>
  <div>
    <div style="height:120px;" />
    <v-row>
      <v-col sm="1"></v-col>
      <v-col sm="2">
        <div style="min-width:100%;display-flex;flex-direction: column;">
          <v-text-field v-model="searchTitle" label="标题"></v-text-field>
          <v-btn block color="primary" @click="getData()">搜 索</v-btn>
          <v-btn v-if="logged" block color="success" class="mt-3" to="/album/management">我的相册</v-btn>
          <v-btn @click="$router.back(-1)" class="mt-3" block color="success" dark>返 回</v-btn>
        </div>
      </v-col>
      <v-col>
        <div>
          <v-card
            class="mx-auto mt-4"
            width="100%"
            elevation="8"
            outlined
            v-for="(item, i) in datas"
            :key="i"
          >
            <v-list-item three-line>
              <v-list-item-content>
                <span style="ml-5 mr-5" class="title"><h2>{{ item.albumName }}</h2></span>
              </v-list-item-content>
              <v-list-item-content>
                 <v-img :src="getThumb(item.albumCover)"
              :lazy-src="load"
                max-height="100"
                max-width="150"
              />
              </v-list-item-content>

              <v-list-item-avatar tile size="80" :color="randomRgb()"></v-list-item-avatar>
            </v-list-item>

            <v-card-actions style="display:flex;justify-content:space-between">
              <span style="ml-5 mr-5">作者: {{item.userName}}</span>
              <span
                style="ml-5 mr-5"
              >更新日期:{{ new Date(Date.parse(item.dateTime)).toLocaleString() }}</span>
              <v-btn text :to="getTarget(item.albumId)">Detail</v-btn>
            </v-card-actions>
          </v-card>
        </div>
        <v-pagination v-model="page" :length="pagelength" class="ma-7"></v-pagination>
      </v-col>
      <v-col sm="1"></v-col>
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
    load: require('./asset/cloud.png'),
    errorAlter: false,
    logged: false,
    pagelength: 1,
    page: 1,
    searchTitle: '',
    datas: []
  }),
  methods: {
    getTarget (id) {
      return 'album/detail/' + id
    },
    getEditTarget (id) {
      return 'album/edit/' + id
    },
    getData () {
      let url =
        '/album/search?albumName=' +
        this.searchTitle +
        '&pageNum=' +
        this.page +
        '&pageSize=10'
      this.$axios
        .get(url)
        .then((res) => {
          if (res.status === 200) {
            this.datas = res.data.list
            this.pagelength = res.date.pages
          }
        })
        .catch((err) => {
          if (err) {
            this.error = err.response.data ? err.response.data : 'error'
            this.errorAlter = true
          }
        })
    },
    getThumb (id) {
      return 'http://' + window.location.host + '/api/photo/' + id + '/thumb'
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
  created () {},
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
