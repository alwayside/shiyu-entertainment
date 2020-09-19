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
            <v-col><v-file-input v-model="uploadImage" label="添加图片" chips></v-file-input></v-col>
            <v-col sm=2><v-btn @click="upload()" class="mt-3" block color="primary" dark>上 传</v-btn></v-col>
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
           <v-card-actions class="mt-0 pt-0 d-flex justify-center">
             <v-row class="pa-0 ma-0">
               <v-col class="pa-0 ma-0">
                 <v-btn v-if="parseInt(item.id) === parseInt(album.albumCover)" disabled block >当前封面</v-btn>
                 <v-btn v-else @click="setCover(item.id)" block color="primary" dark>设为封面</v-btn>
               </v-col>
               <v-col class="pa-0 ma-0"><v-btn @click="deleteById(item.id)" block color="error" dark>删 除</v-btn></v-col>
             </v-row>
             </v-card-actions>
          </v-card>

        </div>
        <v-pagination
        @input="pageChange"
          v-model="page"
          :length="pagelength" class="ma-7"></v-pagination>
      </v-col>
      <v-col sm="1"></v-col>
    </v-row>

    <v-dialog v-model="detail">
      <img :src="seeDetail()">
    </v-dialog>
     <v-dialog v-model="delDialog" persistent max-width="400">
      <v-card>
        <v-card-title class="headline">确定删除？</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="delDialog = false">关闭</v-btn>
          <v-btn color="green darken-1" text @click=deletItem() >确定</v-btn>
        </v-card-actions>
      </v-card>
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
    setCover (id) {
      let url = '/album/' + this.id + '/cover/' + id
      this.$axios
        .put(url)
        .then((res) => {
          if (res.status === 200) {
            this.getData()
            this.getAlbum()
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
    upload () {
      if (this.uploadImage === null) {
        return
      }
      let url = '/album/photo/' + this.id
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      const param = new FormData()
      param.append('file', this.uploadImage)
      this.$axios.post(url, param, config).then((res) => {
        if (res.status === 200) {
          this.uploadImage = null
          this.successAlter = true
          this.getData()
        }
      }).catch((err) => {
        if (err) {
          this.error = err.response.data ? err.response.data : 'error'
          this.errorAlter = true
        }
      })
    },
    deleteById (id) {
      this.delDialog = true
      this.deleteId = id
    },
    deletItem () {
      let url = '/album/delete/' + this.id + '/' + this.deleteId
      this.$axios
        .delete(url)
        .then((res) => {
          if (res.status === 200) {
            this.delDialog = false
            this.deleteId = 0
            this.successAlter = true
            this.getData()
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
