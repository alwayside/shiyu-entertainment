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
    color="#ffffff"
    height="60px"
    tile
    opacity="0.7"
    class="appBarColor"
    fixed
    elevate-on-scroll

  ><router-link to="/" @click.native="btnOn(4)"><span class="d-flex align-center">
     <v-col width="60px"/>
     <img src="" alt="hbc-medical" height="60">
   </span></router-link>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-layout row wrap>
      <v-flex lg12>
    <v-btn
      elevation="0"
      tile
      :color="color[4]"
      @click.native="btnOn(4)"
      class="appBarBtn"
      :height=appBarHeight
      to="/"
    >
      {{$t('homePage')}}
    </v-btn>
    <v-btn
      elevation="0"
      tile
      :color="color[0]"
      @click.native="btnOn(0)"
      class="appBarBtn"
      :height=appBarHeight
      to="/medicine"
    >
      {{ $t('medicine')}}
    </v-btn>
    <v-btn
      elevation="0"
      tile
      :color="color[1]"
      @click.native="btnOn(1)"
      class="appBarBtn"
      :height=appBarHeight
      to="/purchase"
    >
      {{ $t('purchase')}}
    </v-btn>
    <v-btn
      elevation="0"
      tile
      :color="color[2]"
      @click.native="btnOn(2)"
      class="appBarBtn"
      :height=appBarHeight
      to="/sell"
    >
      {{ $t('sell')}}
    </v-btn>
    <v-btn
      elevation="0"
      tile
      :color="color[3]"
      @click.native="btnOn(3)"
      class="appBarBtn"
      :height=appBarHeight
      to="/store"
    >
      {{ $t('remain')}}
    </v-btn>
     <v-btn elevation="0"

      tile
      :color="color[5]"
      @click.native="btnOn(5)"
      class="appBarBtn"
      :height=appBarHeight
      to="/finance"
          >
            {{$t('finance')}}
          </v-btn>

    <!--language select button-->
    <v-menu attach="" bottom left offset-y>
      <template v-slot:activator="{ on }">
        <v-btn text v-on="on" :height=appBarHeight class="languageBtn" @mouseenter="languageIcon = true" @mouseleave="languageIcon = false">
          {{selectedLang}}<v-icon v-if="languageIcon">mdi-earth</v-icon>
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
    appBarHeight: '',
    selectedLang: '',
    helpMenu: [
      'help',
      'FAQ'
    ],
    lang: [
      '简体中文',
      'English'
    ],
    color: [],
    languageIcon: false
  }),
  methods: {
    toHelp: function (index) {
      if (index === 1) {
        return '/FAQ'
      }
      return '/Help'
    },
    setAppBarHeight: function () {
      this.appBarHeight = '60px'
    },
    setColor: function () {
      this.color = [
        'transparent',
        'transparent',
        'transparent',
        'transparent',
        'transparent',
        'transparent'
      ]
    },
    btnOn: function (btn) {
      this.color = window.localStorage.getItem('btnColor').split(',')
      // this.setColor()
      // this.color[btn] = '#4caf50'
      this.color.splice('0', 0)
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
      this.color = window.localStorage.getItem('btnColor').split(',')
      this.color.splice('0', 0)
    }
  },
  computed: {
    area () {
      return this.$i18n.locale
    },
    getColor (index) {
      // this.setColor()
      this.btnOn(index)
      return this.color[index]
    }
  },
  mounted: function () {
    this.color = window.localStorage.getItem('btnColor').split(',')
    this.color.splice('0', 0)
    this.setAppBarHeight()
    if (this.$i18n.locale === 'zh') {
      this.selectedLang = '简体中文'
    } else {
      this.selectedLang = 'English'
    }
  }
}
</script>

<style scoped>

</style>
