package com.saechim.saechimlog.service

import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.dto.PostResponse
import com.saechim.saechimlog.dto.PostSearch
import com.saechim.saechimlog.repository.PostRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    val postRepository: PostRepository,
) {
    val log: Logger = LoggerFactory.getLogger(PostService::class.java)

    fun write(postCreate: PostCreate) : Post{
        return postRepository.save(postCreate.toPostEntity())

    }

    fun getPost(id:Long) : PostResponse = postRepository.findByIdOrNull(id)?.let {
        return PostResponse.from(it)
    } ?: kotlin.run {
        throw IllegalArgumentException("존재하지 않는 글입니다")
    }

    fun getList(postSearch: PostSearch): List<PostResponse> {
        val postResponseList = mutableListOf<PostResponse>()
        postRepository.getList(postSearch).mapTo(postResponseList) { PostResponse.from(it) }
        return postResponseList
    }
}