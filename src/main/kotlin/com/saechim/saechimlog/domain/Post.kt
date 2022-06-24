package com.saechim.saechimlog.domain

import javax.persistence.*

@Entity
class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,

    @Lob
    var content: String,

    ) {
    fun editPost(postEditor: PostEditor) {
        this.title = postEditor.title ?: this.title
        this.content = postEditor.content ?: this.content
    }

}