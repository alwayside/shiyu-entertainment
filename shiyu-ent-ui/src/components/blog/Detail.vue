<template>
  <div :style="backgroundDiv">
    <div style="height:120px;" />
    <v-row>
      <v-col sm="1"></v-col>
      <v-col>
        <br />
        <v-card class="mx-auto" width="100%">
          <v-card-title class="d-flex justify-center">
            <span style="fontSize:44px" class="ma-4">{{data.title}}</span>
          </v-card-title>
          <v-card-subtitle>作者: {{data.userName}} 更新日期:{{ new Date(Date.parse(data.dateTime)).toLocaleString() }}</v-card-subtitle>
          <v-card-text class="d-flex justify-start">
            <div v-html="data.content" />
          </v-card-text>
          <v-card-actions></v-card-actions>
        </v-card>
      </v-col>
      <v-col sm="1"></v-col>
    </v-row>
    <v-row>
      <v-col sm="1"></v-col>
      <v-col>
        <v-card class="mx-auto" width="100%">
          <v-card-title class="d-flex justify-start">
            <span style="fontSize:44px" class="ma-4">Comments</span>
          </v-card-title>
          <v-card-text v-if="commentData.length > 0">
            <v-sheet
                   class="mt-2"
                  v-for="(item, i) in commentData"
                  :key=i
                  width="100%"
                  elevation="3"
                  tile
              >
              <div class="d-flex justify-start pa-3">
                <div style="fontSize:24px;color:blue;white-space:nowrap" class="pa-2 d-flex justify-start"><span>{{item.userName}} :</span></div>
                <div style="fontSize:24px;word-break: break-all;text-align: left;" class="pa-2 d-flex justify-start">{{item.content}}</div>
              </div>
              <div style="fontSize:18px" class="d-flex justify-start pl-3 pb-3 pr-3">
                <div>发表时间: {{new Date(Date.parse(item.dateTime)).toLocaleString()}}</div>
                <div style="width:10%"></div>
                <div>回复: {{item.comments}}</div>
                <div style="width:10%"></div>
              </div>
              </v-sheet>
            <v-row justify="center">
            <v-pagination
            v-model="commentPage"
            :length=commentPagelength
            class="ma-2"
          ></v-pagination>
          </v-row>

          </v-card-text>
          <v-divider></v-divider>
          <v-card-text v-if="logged" class="pt-0 pb-0">
            <v-row class="pl-3 pr-3"><span class="title">发表评论</span></v-row>
            <v-row class="pl-3 pr-3">
               <v-textarea v-model="newComment" no-resize auto-grow placeholder="在此评论...."/>
            </v-row>
          </v-card-text>
          <v-card-actions v-if="logged" class="d-flex justify-end">
            <v-btn class="ma-3" color="success" @click="commentIt(null)" dark>发表</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col sm="1"></v-col>
    </v-row>
    <v-row class="d-flex justify-center">
      <v-btn  @click="$router.back(-1)" class="mt-3 mb-3" color="success" dark>返 回</v-btn>
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
    replyId: null,
    commentPage: 1,
    commentPagelength: 1,
    commentData: [
    ],
    logged: false,
    dialog: false,
    id: 0,
    errorAlter: false,
    data: {},
    newComment: '',
    backgroundDiv: {
      height: '100%',
      backgroundImage: 'url(https://picsum.photos/1920/1080?random)',

      // backgroundRepeat: 'no-repeat',
      backgroundAttachment: 'fixed',
      backgroundSize: '100% 100%'
    }
  }),
  methods: {
    getData (id) {
      if (id == null) {
        return
      }
      let url = '/blog/search/' + id
      this.$axios
        .get(url)
        .then((res) => {
          if (res.status === 200) {
            this.data = res.data
          }
        })
        .catch((err) => {
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
    },
    commentIt (id) {
      if (this.newComment.length < 1) {
        return
      }
      let url = '/comment/article/' + this.id
      let commentDo = {
        content: this.newComment
      }
      console.log(id)
      if (id) {
        commentDo.reply = id
      }
      this.$axios
        .post(url, commentDo)
        .then((res) => {
          if (res.status === 200) {
            this.newComment = ''
            this.getCommentDataEnd()
          }
        })
        .catch((err) => {
          if (err) {
            this.error = err.response.data ? err.response.data : 'error'
            this.errorAlter = true
          }
        })
    },
    getCommentData () {
      if (this.id == null) {
        return
      }
      let url = '/comment/article/' + this.id + '?pageNum=' + this.commentPage + '&pageSize=10'
      this.$axios
        .get(url)
        .then((res) => {
          if (res.status === 200) {
            this.commentData = res.data.list
            this.commentPagelength = res.date.pages
          }
        })
        .catch((err) => {
          if (err) {
            this.error = err.response.data ? err.response.data : 'error'
            this.errorAlter = true
          }
        })
    },
    // 获取最后一页评论 （最新）
    getCommentDataEnd () {
      if (this.id == null) {
        return
      }
      let url = '/comment/article/' + this.id + '?pageNum=' + this.commentPagelength + '&pageSize=10'
      this.$axios
        .get(url)
        .then((res) => {
          if (res.status === 200) {
            this.commentData = res.data.list
            this.commentPagelength = res.date.pages
          }
        })
        .catch((err) => {
          if (err) {
            this.error = err.response.data ? err.response.data : 'error'
            this.errorAlter = true
          }
        })
    }

  },
  computed: {
    area () {
      return this.$i18n.locale
    }
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
  created () {
  },
  mounted: function () {
    this.getData(this.$route.params.id)
    this.id = this.$route.params.id
    let token = localStorage.getItem('Authorization')
    if (token === null || token === '') {
      this.logged = false
    } else {
      this.logged = true
      this.user = window.localStorage.getItem('User')
    }
    this.getCommentData()
  }
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
