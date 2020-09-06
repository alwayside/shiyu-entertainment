<!--
  - Licensed to the Apache Software Foundation (ASF) under one or more
  - contributor license agreements.  See the NOTICE file distributed with
  - this work for additional information regarding copyright ownership.
  - The ASF licenses this file to You under the Apache License, Version 2.0
  - (the "License"); you may not use this file except in compliance with
  - the License.  You may obtain a copy of the License at
  -
  -     http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -->

<template>
  <v-app-bar
    tile
    fixed
    elevate-on-scroll
    shrink-on-scroll
    prominent
    src="https://picsum.photos/1920/1080?random"
  >
  <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(25,25,112,.5), rgba(72,61,139,.8)"
        ></v-img>
      </template>
  <router-link to="/" @click.native="btnOn(4)"><span class="d-flex align-center">
     <v-col width="60px"/>
     <img src="../../assets/logo.png" alt="shiyu" height="60">
   </span></router-link>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-toolbar-title><div class="d-flex align-end" style="height:100%">
        <v-btn text class="mr-5 title" color="#fff" to="/home">Home</v-btn>
        <v-btn text class="mr-5 title" color="#fff" to="/blog">Blog</v-btn>
        <v-btn text class="mr-5 title" color="#fff" to="/album">Gallery</v-btn>
        <v-btn text class="mr-5 title" color="#fff">HighLight</v-btn>
        </div></v-toolbar-title>
    <v-layout row wrap>
       <div v-if="logged" class="d-flex align-center">
          <span style="color:#fff"><strong>Hello ! {{ user }}</strong></span>
      </div>
      <div v-if="logged">
          <v-btn @click="logout" class="ma-2" color="primary">登出</v-btn>
      </div>
      <div v-if="!logged">
          <v-btn @click="login" class="ma-2" color="primary">登录</v-btn>
      </div>
      <v-flex lg12 style="display:none">
        <!--language select button-->
        <v-menu attach="" bottom left offset-y>
          <template v-slot:activator="{ on }">
            <v-btn text v-on="on" class="languageBtn" @mouseenter="languageIcon = true" @mouseleave="languageIcon = false">
              <span style="color:#fff">{{selectedLang}}<v-icon v-if="languageIcon">mdi-earth</v-icon></span>
            </v-btn>
          </template>
          <v-list class="pa-0">
            <v-list-item v-for="(item, index) in lang" @click="change(index)" :key="index">
              <v-list-item-title>{{item}}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-flex>
    </v-layout>
  </v-app-bar>
</template>
<script>

export default {
  name: 'toolbar',
  data: () => ({
    lang: [
      '简体中文'
      // 'English'
    ],
    languageIcon: false,
    logged: false,
    user: window.localStorage.getItem('User') ? window.localStorage.getItem('User') : ''
  }),
  methods: {
    login () {
      this.$router.push('/login')
    },
    logout () {
      this.$store.dispatch('logout', null)
    },
    change: function (index) {
      this.selectedLang = this.lang[index]
      if (index === 0) {
        this.$i18n.locale = 'zh'
      } else {
        this.$i18n.locale = 'en'
      }
      this.$store.dispatch('changeArea', { area: this.$i18n.locale })
      window.localStorage.setItem('locale', this.$i18n.locale)
      window.localStorage.setItem('selectedLang', this.selectedLang)
    }
  },
  watch: {
    $route () {
    },
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
  computed: {
    area () {
      return this.$i18n.locale
    },
    Authorization () {
      return this.$store.state.Authorization
    }
  },
  mounted: function () {
    if (this.$i18n.locale === 'zh') {
      this.selectedLang = '简体中文'
    } else {
      this.selectedLang = 'English'
    }
    let token = localStorage.getItem('Authorization')
    if (token === null || token === '' || token === undefined) {
      this.logged = false
      console.log(1111111111111111)
    } else {
      this.logged = true
      this.user = window.localStorage.getItem('User')
    }
  }
}
</script>

<style scoped>

</style>
