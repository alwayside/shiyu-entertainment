<template>
<div :style="backgroundDiv">
  <div style="height:120px;"/>
  <v-row>
    <v-col sm=1></v-col>
    <v-col>
         <v-text-field
            v-model="newTitle"
            label="标题"
            filled
          ></v-text-field>
          </v-col>
      <v-col sm=1></v-col>
  </v-row>
  <v-row class="">
    <v-col sm=1></v-col>
    <v-col style="background-color:transparent;min-height:calc(100vh - 400px)">
      <div class="example">
          <quill-editor
          class="editor"
          ref="myTextEditor"
          :value="content"
          :options="editorOption"
          @change="onEditorChange"
          @blur="onEditorBlur($event)"
          @focus="onEditorFocus($event)"
          @ready="onEditorReady($event)"
        />
      </div>

  </v-col>
    <v-col sm=1></v-col>
  </v-row>
  <v-row>
    <v-col sm=1></v-col>
     <v-col>
        <v-btn block color="error" @click="delDialog = true" class="mt-2">
        删除
      </v-btn>
    </v-col>
    <v-col>
      <v-btn @click="$router.back(-1)" class="mt-2" block color='success' dark >
             返 回
      </v-btn>
    </v-col>
    <v-col>
         <v-btn block color="primary" @click="dialog = true" class="mt-2">
        提 交
      </v-btn>
          </v-col>
      <v-col sm=1></v-col>
  </v-row>

  <v-dialog v-model="dialog" persistent max-width="400">
      <v-card>
        <v-card-title class="headline">确定修改？</v-card-title>
        <v-card-text>

        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">Close</v-btn>
          <v-btn color="green darken-1" text @click=addNewBlog() >Submit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

         <v-dialog v-model="delDialog" persistent max-width="400">
      <v-card>
        <v-card-title class="headline">确定删除？</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="delDialog = false">关闭</v-btn>
          <v-btn color="green darken-1" text @click=deleteBlog() >确定</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

              <v-snackbar v-model="successAlter" color="success">
              success
              <v-btn dark text @click="successAlter = false">{{$t('close')}}</v-btn>
            </v-snackbar>
             <v-snackbar v-model="errorAlter" color="error">
              {{error}}
              <v-btn dark text @click="errorAlter = false">{{$t('close')}}</v-btn>
            </v-snackbar>
</div>
</template>

<script>
import bg from './assets/bg2.jpg'

// import dedent from 'dedent'
import hljs from 'highlight.js'
import debounce from 'lodash/debounce'
import { quillEditor } from 'vue-quill-editor'

// highlight.js style
import 'highlight.js/styles/tomorrow.css'

// import theme style
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'

export default {
  name: 'quill-example-snow',
  title: 'Theme: snow',
  components: {
    quillEditor
  },
  data: () => ({
    delDialog: false,
    dialog: false,
    newTitle: 'the title',
    url: bg,
    error: 'ERROR',
    successAlter: false,
    errorAlter: false,
    backgroundDiv: {

      backgroundImage: 'url(' + require('./assets/bg1.jpg') + ')',

      // backgroundRepeat: 'no-repeat',
      backgroundAttachment: 'fixed',
      backgroundSize: '100% 100%'

    },
    editorOption: {
      modules: {
        toolbar: [
          ['bold', 'italic', 'underline', 'strike'],
          ['blockquote', 'code-block'],
          [{ 'header': 1 }, { 'header': 2 }],
          [{ 'list': 'ordered' }, { 'list': 'bullet' }],
          [{ 'script': 'sub' }, { 'script': 'super' }],
          [{ 'indent': '-1' }, { 'indent': '+1' }],
          [{ 'direction': 'rtl' }],
          [{ 'size': ['small', false, 'large', 'huge'] }],
          [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
          [{ 'font': [] }],
          [{ 'color': [] }, { 'background': [] }],
          [{ 'align': [] }],
          ['clean'],
          ['link', 'image', 'video']
        ],
        syntax: {
          highlight: text => hljs.highlightAuto(text).value
        }
      }
    },
    content: ''
  }),
  methods: {
    onEditorChange: debounce(function (value) {
      this.content = value.html
    }, 466),
    onEditorBlur (editor) {
      console.log('editor blur!', editor)
    },
    onEditorFocus (editor) {
      console.log('editor focus!', editor)
    },
    onEditorReady (editor) {
      console.log('editor ready!', editor)
    },
    deleteBlog () {
      this.delDialog = false
      let url = '/blog/delete/' + this.id
      this.$axios.delete(url).then((res) => {
        if (res.status === 200) {
          this.$router.push('/blog')
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
          this.content = res.data.content
          this.newTitle = res.data.title
          this.id = res.data.id
        }
      }).catch((err) => {
        if (err) {
          this.error = err.response.data ? err.response.data : 'error'
          this.errorAlter = true
        }
      })
    },
    addNewBlog () {
      this.dialog = false
      this.successAlter = false
      this.errorAlter = false
      let articleDo = {
        title: this.newTitle,
        content: this.content,
        id: this.id,
        medicinePrice: this.medicinePrice,
        medicineUnit: this.medicineUnit,
        medicineDescription: this.medicineDescription
      }
      let url = '/blog/update'
      this.$axios.put(url, articleDo).then((res) => {
        if (res.status === 200) {
          this.newTitle = ''
          this.content = ''
          this.successAlter = true
        }
      }).catch((err) => {
        if (err) {
          this.error = err.response.data ? err.response.data : 'error'
          this.errorAlter = true
        }
      })
    }
  },
  computed: {
    editor () {
      return this.$refs.myTextEditor.quill
    },
    contentCode () {
      return hljs.highlightAuto(this.content).value
    },
    area () {
      return this.$i18n.locale
    }
  },
  created () {
  },
  mounted: function () {
    console.log('this is Quill instance:', this.editor)
    this.getData(this.$route.params.id)
    this.id = this.$route.params.id
  }
}
</script>

<style>
</style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.example {
  background-color:#fff;
  height: 100%;
  display: flex;
  flex-direction: column;
}
.example .editor {
  height: 100%;
  overflow: auto;
}
.example .output {
  width: 100%;
  height: 20rem;
  margin: 0;
  border: 1px solid #ccc;
  overflow-y: auto;
  resize: vertical;
}
.example .output.code {
  padding: 1rem;
  height: 16rem;
}
.example .output.ql-snow {
  border-top: none;
  height: 24rem;
}

</style>
