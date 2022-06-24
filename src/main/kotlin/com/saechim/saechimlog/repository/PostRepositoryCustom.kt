package com.saechim.saechimlog.repository

import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.dto.PostSearch

interface PostRepositoryCustom {

    fun getList(postSearch: PostSearch) :List<Post>
}