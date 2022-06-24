package com.saechim.saechimlog.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.domain.QPost.*
import com.saechim.saechimlog.dto.PostSearch

class PostRepositoryImpl(
    val jpaQueryFactory: JPAQueryFactory
) : PostRepositoryCustom {

    override fun getList(postSearch: PostSearch): List<Post> {
        return jpaQueryFactory.selectFrom(post)
            .limit(postSearch.size.toLong())
            .offset(postSearch.getOffset())
            .orderBy(post.id.desc())
            .fetch()
    }

}