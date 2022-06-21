package com.saechim.saechimlog.service

import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.repository.PostRepository
import com.saechim.saechimlog.request.PostCreate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PostService(
    val postRepository: PostRepository,
) {
    val log: Logger = LoggerFactory.getLogger(PostService::class.java)

    fun write(postCreate: PostCreate){
        postRepository.save(Post.createPost(postCreate))
    }
}