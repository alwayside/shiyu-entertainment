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
  <v-footer
    inset
    height="60px"
    class="pa-0 footer-border-top d-flex justify-center"
    style="background: #ffffff!important;"
  >
    <span class="caption"><strong>Copyright</strong> © 2019-2020 <strong>SHIYU-ENTERTAIMENT  {{$t('HBCMISTitle')}}</strong></span>
  </v-footer>
</template>
<script>
export default {
  name: 'footers'
}
</script>

<style>
  .footer-border-top {
    border-top: solid 0px;
    /*background-image: linear-gradient(to right , #FFFFFF, #43A047 )*/
    position: relative;
  }
</style>
