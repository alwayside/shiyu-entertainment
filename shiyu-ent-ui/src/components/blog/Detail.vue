<template>
<div :style="backgroundDiv">
  <div style="height:120px;"/>
  <v-row>
    <v-col sm=1></v-col>
    <v-col>
    <v-btn @click="$router.back(-1)" class="mt-3 mb-3" color='success' dark >
             返 回
      </v-btn>
      <v-card
    class="mx-auto"
    width="100%"
  >
  <v-card-title class="d-flex justify-center"><span style="fontSize:44px" class="ma-4">{{data.title}}</span></v-card-title>
  <v-card-subtitle>作者: Admin    更新日期:{{ new Date(Date.parse(data.dateTime)).toLocaleString() }}          </v-card-subtitle>
    <v-card-text class="d-flex justify-start">
     <div v-html="data.content" />
    </v-card-text>
    <v-card-actions>
      <v-btn
        text
        color="deep-purple accent-4"
        @click="dialog = true"
      >
        删除
      </v-btn>
    </v-card-actions>
  </v-card>

  <v-dialog v-model="dialog" persistent max-width="400">
      <v-card>
        <v-card-title class="headline">确定删除？</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">关闭</v-btn>
          <v-btn color="green darken-1" text @click=deleteBlog() >确定</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
    dialog: false,
    id: 0,
    errorAlter: false,
    data: {},
    backgroundDiv: {
      height: '100%',
      backgroundImage: 'url(https://picsum.photos/1920/1080?random)',

      // backgroundRepeat: 'no-repeat',
      backgroundAttachment: 'fixed',
      backgroundSize: '100% 100%'
    }
  }),
  methods: {
    deleteBlog () {
      this.dialog = false
      let url = '/blog/delete/' + this.id
      this.$axios.delete(url).then((res) => {
        if (res.status === 200) {
          this.$router.back(-1)
        }
      }).catch((err) => {
        if (err) {
          this.error = err.response.data ? err.response.data : 'error'
          this.errorAlter = true
        }
      })
    },
    getData (id) {
      if (id == null) {
        return
      }
      let url = '/blog/search/' + id
      this.$axios.get(url).then((res) => {
        if (res.status === 200) {
          console.log(res.data.list)
          this.data = res.data
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
  mounted: function () {
    this.getData(this.$route.params.id)
    this.id = this.$route.params.id
  }
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
