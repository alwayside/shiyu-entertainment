<template>
  <div class="d-flex justify-center align-center" style="width:100%;height:100%">

    <h1 class="ml12" style="color: cornsilk">
      Welcome!
      <span class="thin" style="">SHIYU-ENTERTAIMENT</span>
    </h1>
     <div id="particlesjs" style="position: absolute;width: 100%;height: 100vh;z-index: -1;background-color:#000;top:0">
        </div>
  <!-- /container -->

  </div>

</template>
<script src="../../static/js/anime12.min.js"></script>
<script>
// eslint-disable-next-line no-unused-vars
import particles from 'particles.js'

export default {
  name: 'HomePage',
  data: () => ({
    name: 'hello'
  }),
  methods: {},
  computed: {
    area () {
      return this.$i18n.locale
    }
  },
  created () {},
  mounted: function () {
    // eslint-disable-next-line no-undef
    particlesJS.load('particlesjs', 'static/particles.json', function () {
      console.log('particles.js loaded - callback')
    })
    let textWrapper = document.querySelector('.ml12');
textWrapper.innerHTML = textWrapper.textContent.replace(/\S/g, "<span class='letter'>$&</span>");

anime.timeline({loop: 1})
  .add({
    targets: '.ml12 .letter',
    translateX: [40,0],
    translateZ: 0,
    opacity: [0,1],
    easing: "easeOutExpo",
    duration: 1200,
    delay: (el, i) => 500 + 30 * i
  })
  .add({
    targets: '.ml12 .letter',
    translateX: [0,-30],
    opacity: [1,1],
    easing: "easeInExpo",
    duration: 1100,
    delay: (el, i) => 100 + 30 * i
  });
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
</style>
