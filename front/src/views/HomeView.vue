<script setup lang="ts">

import axios from "axios";
import {ref} from "vue";
import router from "@/router";

const posts = ref([])

axios.get("/api/posts?page=1&size=5").then((response) => {
  response.data._embedded.postResponseList.forEach((r: any) => {
    posts.value.push(r)
  });

});

const moveToRead = () => {
  router.push({name: "read"})
};


</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title">
        <router-link :to="{ name: 'read' , params: {postId: post.id } }"> {{ post.title }}</router-link>
      </div>

      <div class="content">
        {{ post.content }}
      </div>


      <div class="sub d-flex">
          <div class="category">개발</div>
          <!--  개발하는 거 깜박해서 하드코딩        -->
          <div class="regDate">2022-06-28</div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">

ul {
  list-style: none;
}

li {
  margin-bottom: 2rem;
  padding: 0;

  .title {
    a {
      font-size: 1.7rem;
      color: #5BB2FF;
      text-decoration: none;
    }

    &:hover {
      text-decoration: underline;
    }
  }

  .content {
    font-size: 0.95rem;
    color: #5d5d5d;
    margin-top: 8px;
  }

  &:last-child {
    margin-bottom: 0;
  }

  .sub{
    margin-top: 4px;
    font-size: 0.8rem;

    .regDate{
      margin-left: 10px;
      color: #6b6b6b
    }
  }
}



</style>
