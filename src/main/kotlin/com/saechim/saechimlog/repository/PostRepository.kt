package com.saechim.saechimlog.repository

import com.saechim.saechimlog.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post,Long> {
}