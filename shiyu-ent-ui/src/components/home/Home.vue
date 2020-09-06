<template>
<div style="height:100vh;overflow:hidden"  >
    <div style="height:60px"/>
    <v-carousel
        cycle
        height="100%"
        hide-delimiter-background
        show-arrows-on-hover
    >
        <v-carousel-item
        v-for="(slide, i) in slides"
        :key="i"
        :src=slide.src
        >
        <div style="height:100%">
            <v-row
                class="fill-height"
                align="center"
                justify="center"
                style="flex-direction: colomn;"
            >
                <div class="display-3">{{ slide.text }}</div>
                <div v-for="(button, i) in slide.buttons" :key="i">
                    <v-btn tile x-large color="primary" :to=button.dir class="ml-5">{{button.text}}</v-btn>
                </div>
            </v-row>
        </div>

        </v-carousel-item>
    </v-carousel>
</div>
</template>

<script>
import bg1 from './assets/BG1.png'
import bg2 from './assets/BG2.jpg'
import bg3 from './assets/BG3.jpg'

export default {
  name: 'HomePage',
  data: () => ({
    colors: [
      'indigo',
      'warning',
      'pink darken-2',
      'red lighten-1',
      'deep-purple accent-4'
    ],
    slides: [
      {
        text: '',
        src: bg1
      },
      {
        text: 'Gallery',
        src: bg2,
        buttons: [{
          text: 'See more',
          dir: '/album'
        }]
      },
      {
        text: 'HighLight',
        src: bg3
      }
    ]
  }),
  methods: {
  },
  computed: {
    area () {
      return this.$i18n.locale
    }
  },
  created () {
  },
  mounted: function () {
  }
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
