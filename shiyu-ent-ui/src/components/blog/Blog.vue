<template>
<div>
<v-row>
  <v-col sm=1></v-col>
  <v-col sm=2></v-col>
  <v-col >11</v-col>
  <v-col sm=1></v-col>
</v-row>
</div>
</template>

<script>


export default {
  name: 'HomePage',
  data: () => ({
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
