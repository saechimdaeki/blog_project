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
      <div>
        <router-link :to="{ name: 'read' , params: {postId: post.id } }"> {{ post.title }}</router-link>
      </div>

      <div>
        {{ post.content }}
      </div>

    </li>
  </ul>
</template>

<style scoped>
li {
  margin-bottom: 1rem;
}

li:last-child {
  margin-bottom: 0;
}
</style>
