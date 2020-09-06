<template>
  <div>
    <div style="height:120px;" />
    <v-row ><br /></v-row>
    <v-row>
      <v-col ></v-col>
      <v-col sm="2"><h1>相册管理</h1></v-col>
      <v-col ></v-col>
    </v-row>
    <v-row>
      <v-col sm="1"></v-col>
      <v-col sm="2">
        <div style="min-width:100%;display-flex;flex-direction: column;">
          <v-text-field v-model="searchTitle" label="标题"></v-text-field>
          <v-btn block color="primary" @click="getData()">搜 索</v-btn>
          <v-btn class="mt-3" block color="success" @click="addAlbum()">添加相册</v-btn>
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
              <span style="ml-5 mr-5">作者: Admin</span>
              <span
                style="ml-5 mr-5"
              >更新日期:{{ new Date(Date.parse(item.dateTime)).toLocaleString() }}</span>
              <v-btn text @click="deleteById(item.albumId)">Delete</v-btn>
              <v-btn text @click="editAlbum(item)">Edit</v-btn>
              <v-btn text :to="getEditTarget(item.albumId)">Detail</v-btn>
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

    <v-dialog v-model="add" persistent max-width="400">
      <v-card>
        <v-card-title class="headline">输入新名称</v-card-title>
        <v-card-text>
          <v-text-field
            v-model="title"
            label="标题"
          ></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="add = false">Close</v-btn>
          <v-btn color="green darken-1" text @click=addNewAlbum() >Submit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="edit" persistent max-width="400">
      <v-card>
        <v-card-title class="headline">输入名称</v-card-title>
        <v-card-text>
          <v-text-field
            v-model="title"
            label="标题"
          ></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="edit = false">Close</v-btn>
          <v-btn color="green darken-1" text @click="updateAlbum()" >Submit</v-btn>
        </v-card-actions>
      </v-card>
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
    title: '',
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
    getTarget (id) {
      return 'album/detail/' + id
    },
    getEditTarget (id) {
      return 'edit/' + id
    },
    deleteById (id) {
      this.delDialog = true
      this.deleteId = id
    },
    deletItem () {
      let url = '/album/delete/' + this.deleteId
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
          }
        })
    },
    editAlbum (item) {
      this.editItem = item.albumId
      this.edit = true
      this.title = item.albumName
    },

    updateAlbum () {
      let url = '/album/update/' + this.editItem
      let albumDo = {
        albumName: this.title
      }
      if (this.title === '') {
        albumDo.albumName = 'edit'
      }
      this.$axios.put(url, albumDo).then((res) => {
        if (res.status === 200) {
          this.edit = false
          this.title = ''
          this.editItem = 0
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

    addAlbum () {
      this.add = true
      this.title = ''
    },
    addNewAlbum () {
      let url = '/album/save'
      let albumDo = {
        albumId: 0,
        albumName: this.title,
        userId: localStorage.getItem('Userid'),
        date: null,
        albumCover: null
      }
      if (this.title === '') {
        albumDo.albumName = 'new'
      }
      this.$axios.post(url, albumDo).then((res) => {
        if (res.status === 200) {
          this.add = false
          this.title = ''
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
    getData () {
      let url =
        '/album/search/' +
        localStorage.getItem('Userid') +
        '/?albumName=' +
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
  }
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
