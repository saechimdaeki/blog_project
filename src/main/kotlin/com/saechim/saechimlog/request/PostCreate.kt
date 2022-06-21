package com.saechim.saechimlog.request

import com.saechim.saechimlog.domain.Post
import javax.validation.constraints.NotBlank

data class PostCreate(

    @field:NotBlank(message = "타이틀을 입력해주세요.")
    val title:String,

    @field:NotBlank(message = "콘텐츠를 입력해주세요")
    val content: String
    ){
    fun toPostEntity() = Post(
        title = this.title,
        content = this.content
    )
}