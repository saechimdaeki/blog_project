<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  },
});

const post = ref({
  id:0,
  title:"",
  content:""
})


const moveToEdit = () =>{
  router.push({name:"edit" , params: {postId: props.postId}})
}

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    // console.log(response)
    post.value = response.data
  })
})

</script>

<template>
  <el-row>
    <el-col>
      <h2 class="title">{{ post.title }}</h2>
    </el-col>
  </el-row>


  <el-row>
    <el-col>
      <div class="content">{{ post.content }}</div>
    </el-col>
  </el-row>

  <el-row>
    <el-clo>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-clo>
  </el-row>
</template>


<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #5BB2FF;
}

.content{
  font-size: 0.95rem;
  color: #5d5d5d;
  margin-top: 8px;
  margin-bottom: 10px;
  white-space: break-spaces;
}

.sub {
  margin-top: 4px;
  font-size: 0.8rem;
}

</style>