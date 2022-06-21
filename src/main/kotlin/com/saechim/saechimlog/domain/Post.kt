package com.saechim.saechimlog.domain

import com.saechim.saechimlog.request.PostCreate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob

@Entity
class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? =null,

    var title : String,

    @Lob
    var content : String,

) {
    companion object{
        fun createPost(postCreate: PostCreate) : Post{
            return Post(
                title = postCreate.title,
                content = postCreate.content
            )
        }
    }
}