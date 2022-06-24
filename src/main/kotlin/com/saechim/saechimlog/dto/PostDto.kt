package com.saechim.saechimlog.dto

import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.domain.PostEditor
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
            title = post.title.substring(0, minOf(post.title.length,10)), //최대 0 글자만 전달해야 한다는 요구사항
            content = post.content
        )
    }
}


data class PostSearch(

    val page: Int = 1,
    val size: Int = 20,
) {
    fun getOffset(): Long {
        return ((maxOf(1, page) - 1) * minOf(size, 2000)).toLong()
    }
}


class PostEdit(
    val title: String? = null,
    val content: String? = null,
) {
    companion object {
        fun toEditor(postEdit: PostEdit) = PostEditor(
            title = postEdit.title,
            content = postEdit.content
        )
    }
}
