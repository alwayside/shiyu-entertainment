<template>
  <div>
    <div style="height:120px;" />
    <v-row ><br /></v-row>
    <v-row>
      <v-col ></v-col>
      <v-col sm="2"></v-col>
      <v-col sm="2"><h1>{{album.albumName}}</h1></v-col>
      <v-col sm="2"></v-col>
      <v-col ></v-col>
    </v-row>
    <v-row>
      <v-col sm="1"></v-col>
      <v-col>
        <div>
          <v-row>
            <v-col></v-col>
            <v-col sm=2></v-col>
            <v-col sm=2><v-btn @click="$router.back(-1)" class="mt-3" block color="success" dark>返 回</v-btn></v-col>
          </v-row>
        </div>
        <div class="d-flex justify-start">

          <v-card
            class="ml-2 mt-1"
            width="310"
            elevation="8"
            outlined
            v-for="(item, i) in datas"
            :key="i"
          >
          <v-card-text class="pa-1">
            <div @click="detail = true; detailId = item.id">
              <v-img :src="getThumb(item.id)"
              :lazy-src="load"
                max-height="200"
                max-width="300"
              />
            </div>
          </v-card-text>
          </v-card>

        </div>
        <v-pagination @input="pageChange" v-model="page" :length="pagelength" class="ma-7"></v-pagination>
      </v-col>
      <v-col sm="1"></v-col>
    </v-row>

    <v-dialog v-model="detail">
      <img :src="seeDetail()">
    </v-dialog>

            <v-snackbar v-model="successAlter" color="success">
              success
              <v-btn dark text @click="successAlter = false">{{$t('close')}}</v-btn>
            </v-snackbar>
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
    detail: false,
    detailId: 0,
    images: [],
    uploadImage: null,
    album: {},
    id: 0,
    editItem: 0,
    deleteId: 0,
    delDialog: false,
    add: false,
    edit: false,
    errorAlter: false,
    successAlter: false,
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
    seeDetail () {
      if (this.detailId === 0) {
        return
      }
      return 'http://' + window.location.host + '/api/photo/' + this.detailId + '/photo'
    },
    getData () {
      let url =
        '/photo/' + this.id +
        '?pageNum=' +
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
            if (err.response.status === 511) {
              this.$store.dispatch('logout', null)
              this.$router.push('/login')
            }
          }
        })
    },
    getThumb (id) {
      return 'http://' + window.location.host + '/api/photo/' + id + '/thumb'
    },
    getAlbum () {
      let url = '/album/' + this.id
      this.$axios
        .get(url)
        .then((res) => {
          if (res.status === 200) {
            this.album = res.data
          }
        })
        .catch((err) => {
          if (err) {
            this.error = err.response.data ? err.response.data : 'error'
            this.errorAlter = true
            if (err.response.status === 511) {
              this.$store.dispatch('logout', null)
              this.$router.push('/login')
            }
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
    this.images = [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ']
    this.id = this.$route.params.id
    this.getAlbum()
    this.getData()
  }
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
