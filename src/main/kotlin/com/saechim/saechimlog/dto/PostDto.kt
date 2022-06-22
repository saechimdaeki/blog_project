package com.saechim.saechimlog.dto

import com.saechim.saechimlog.domain.Post
import javax.validation.constraints.NotBlank

class PostCreate(

    @field:NotBlank(message = "타이틀을 입력해주세요.")
    val title: String,

    @field:NotBlank(message = "콘텐츠를 입력해주세요")
    val content: String,
) {
    fun toPostEntity() = Post(
        title = this.title,
        content = this.content
    )
}

class PostResponse(

    val title: String,

    val content: String
){
    companion object{
        fun from(post: Post) = PostResponse(
            title = post.title,
            content = post.content
        )
    }
}