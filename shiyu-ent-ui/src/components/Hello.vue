<template>
  <div class="d-flex justify-center align-center flex-column" style="width:100%;height:100%">
      <h1 class="ml12" style="color: cornsilk">
        Welcome! SHIYU-ENTERTAIMENT
      </h1>
    <div class="mt-5 btnInto">
      <v-btn tile x-large color="primary" to='/home' outlined>进 入 SHIYU 的 空 间</v-btn>
    </div>
     <div id="particlesjs" style="position: absolute;width: 100%;height: 100vh;z-index: -1;background-color:#000;top:0">
    </div>
  <!-- /container -->

  </div>

</template>

<script>
// eslint-disable-next-line no-unused-vars
import particles from 'particles.js'

export default {
  name: 'HomePage',
  data: () => ({
    name: 'hello'
  }),
  methods: {
    createdTitle () {
      let textWrapper = document.querySelector('.ml12')
      if (textWrapper === null) {
        setTimeout(() => this.createdTitle(), 200)
        return
      }
      console.log(textWrapper)
      textWrapper.innerHTML = textWrapper.textContent.replace(/\S/g, "<span class='letter'>$&</span>")
      // eslint-disable-next-line
    anime.timeline({loop: true})
        .add({
          targets: '.ml12 .letter',
          translateX: [40, 0],
          translateZ: 0,
          opacity: [0, 1],
          easing: 'easeOutExpo',
          duration: 1200,
          delay: (el, i) => 600 + 30 * i
        })
        .add({
          targets: '.ml12 .letter',
          translateX: [0, -30],
          opacity: [1, 0],
          easing: 'easeInExpo',
          duration: 2400,
          delay: (el, i) => 100 + 30 * i
        })
    }
  },
  computed: {
    area () {
      return this.$i18n.locale
    }
  },
  created () {
    this.createdTitle()
  },
  mounted: function () {
    // eslint-disable-next-line no-undef
    particlesJS.load('particlesjs', 'static/particles.json', function () {
      console.log('particles.js loaded - callback')
    })
    this.createdTitle()
  }
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.ml12 {
  font-weight: 200;
  font-size: 1.8em;
  text-transform: uppercase;
  letter-spacing: 0.5em;
}

.ml12 .letter {
  display: inline-block;
  line-height: 1em;
}

.btnInto {
  opacity: 0;
  animation: btnKey 1.5s forwards 2s;
}

@keyframes btnKey {
  to {
    opacity: 1;
  }
}

</style>
