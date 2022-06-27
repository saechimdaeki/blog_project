<script setup lang="ts">


import {useRouter} from "vue-router";
import {ref} from "vue";
import axios from "axios";

const router= useRouter();

const post = ref({
  id:0,
  title:"",
  content:""
})

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  },
});

axios.get(`/api/posts/${props.postId}`).then((response) => {
  // console.log(response)
  post.value = response.data
})

const edit = () =>{
  axios.patch(`/api/posts/${props.postId}`, post.value).then((response) => {
    // console.log(response)
    router.replace({name: "home"})
  });
}

</script>

<template>
  <div>
    <el-input v-model="post.title"/>
  </div>
  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15"></el-input>
  </div>
  <div>
    <el-button type="primary" @click="edit()">글 수정 완료</el-button>
  </div>
</template>

<style>

</style>