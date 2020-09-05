<template>
  <div :style="backgroundDiv">
    <v-row style="height:180px">
      <v-col class="transparent" />
      <v-col sm="6" style="background-color:#fff" />
      <v-col class="transparent" />
    </v-row>
    <v-row align="center" justify="center">
      <v-col class="transparent" />
      <v-col sm="6" align="center" justify="center" style="background-color:#fff">
        <div style="width:30px" />
        <div class="sk-folding-cube">
          <div class="sk-cube1 sk-cube"></div>
          <div class="sk-cube2 sk-cube"></div>
          <div class="sk-cube4 sk-cube"></div>
          <div class="sk-cube3 sk-cube"></div>
        </div>
        <div class="title">
          <strong>请输入登录信息</strong>
        </div>
      </v-col>
      <v-col class="transparent" />
    </v-row>
    <v-row>
      <v-col class="transparent" />
      <v-col sm="1" style="background-color:#fff" />
      <v-col sm="4" style="background-color:#fff">
        <div>
          <v-text-field type="text" outlined v-model="loginForm.account" placeholder="用户名" />
          <v-text-field type="password" outlined v-model="loginForm.passWord" placeholder="密码" />
          <v-btn @click="login" class="ma-2" color="primary">登录</v-btn>
          <v-btn @click="$router.back(-1)" class="ma-2" color='success' dark >
             返 回
          </v-btn>
        </div>
      </v-col>
      <v-col sm="1" style="background-color:#fff" />
      <v-col class="transparent" />
    </v-row>
    <v-row style="height:100%">
      <v-col class="transparent" />
      <v-col sm="6" style="background-color:#fff" />
      <v-col class="transparent" />
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
    error: '',
    backgroundDiv: {
      height: 'calc(100vh - 60px)',
      overflow: 'hidden',
      backgroundImage: 'url(' + require('./assets/bg1.jpg') + ')',
      // backgroundRepeat: 'no-repeat',
      backgroundAttachment: 'fixed',
      backgroundSize: '100% 100%'
    },
    loginForm: {
      account: '',
      passWord: ''
    }
  }),
  methods: {
    login () {
      if (this.loginForm.account === '' || this.loginForm.passWord === '') {
        this.error = '账号或密码不能为空'
        this.errorAlter = true
      } else {
        let url = '/admin/login'
        this.$axios
          .post(url, this.loginForm)
          .then((res) => {
            if (res.status === 200) {
              // 将用户token保存到vuex中
              this.$store.dispatch('login', res.data)
              this.$router.push('/home')
            }
          })
          .catch((err) => {
            console.log(err)
            if (err) {
              this.error = err.response ? err.response.data : 'error'
              this.errorAlter = true
            }
          })
      }
    }
  },
  computed: {
    area () {
      return this.$i18n.locale
    }
  },
  created () {},
  mounted: function () {}
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.sk-folding-cube {
  margin: 20px auto;
  width: 40px;
  height: 40px;
  position: relative;
  -webkit-transform: rotateZ(45deg);
  transform: rotateZ(45deg);
}

.sk-folding-cube .sk-cube {
  float: left;
  width: 50%;
  height: 50%;
  position: relative;
  -webkit-transform: scale(1.1);
  -ms-transform: scale(1.1);
  transform: scale(1.1);
}
.sk-folding-cube .sk-cube:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: royalblue;
  -webkit-animation: sk-foldCubeAngle 2.4s infinite linear both;
  animation: sk-foldCubeAngle 2.4s infinite linear both;
  -webkit-transform-origin: 100% 100%;
  -ms-transform-origin: 100% 100%;
  transform-origin: 100% 100%;
}
.sk-folding-cube .sk-cube2 {
  -webkit-transform: scale(1.1) rotateZ(90deg);
  transform: scale(1.1) rotateZ(90deg);
}
.sk-folding-cube .sk-cube3 {
  -webkit-transform: scale(1.1) rotateZ(180deg);
  transform: scale(1.1) rotateZ(180deg);
}
.sk-folding-cube .sk-cube4 {
  -webkit-transform: scale(1.1) rotateZ(270deg);
  transform: scale(1.1) rotateZ(270deg);
}
.sk-folding-cube .sk-cube2:before {
  -webkit-animation-delay: 0.3s;
  animation-delay: 0.3s;
}
.sk-folding-cube .sk-cube3:before {
  -webkit-animation-delay: 0.6s;
  animation-delay: 0.6s;
}
.sk-folding-cube .sk-cube4:before {
  -webkit-animation-delay: 0.9s;
  animation-delay: 0.9s;
}
@-webkit-keyframes sk-foldCubeAngle {
  0%,
  10% {
    -webkit-transform: perspective(140px) rotateX(-180deg);
    transform: perspective(140px) rotateX(-180deg);
    opacity: 0;
  }
  25%,
  75% {
    -webkit-transform: perspective(140px) rotateX(0deg);
    transform: perspective(140px) rotateX(0deg);
    opacity: 1;
  }
  90%,
  100% {
    -webkit-transform: perspective(140px) rotateY(180deg);
    transform: perspective(140px) rotateY(180deg);
    opacity: 0;
  }
}

@keyframes sk-foldCubeAngle {
  0%,
  10% {
    -webkit-transform: perspective(140px) rotateX(-180deg);
    transform: perspective(140px) rotateX(-180deg);
    opacity: 0;
  }
  25%,
  75% {
    -webkit-transform: perspective(140px) rotateX(0deg);
    transform: perspective(140px) rotateX(0deg);
    opacity: 1;
  }
  90%,
  100% {
    -webkit-transform: perspective(140px) rotateY(180deg);
    transform: perspective(140px) rotateY(180deg);
    opacity: 0;
  }
}
</style>
