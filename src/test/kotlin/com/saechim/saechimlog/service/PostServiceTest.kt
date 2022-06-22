package com.saechim.saechimlog.service

import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.repository.PostRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PostServiceTest(
    @Autowired
    val postService: PostService,

    @Autowired
    val postRepository: PostRepository

){

    @BeforeEach
    fun clean(){
        postRepository.deleteAll()
    }

    @Test
    @DisplayName("글 작성")
    fun `글작성 테스트`(){
        //given
        val postCreate = PostCreate(title = "제목입니다", content = "내용입니다")

        //when
        postService.write(postCreate)

        //then

        assertThat(1L).isEqualTo(postRepository.count())
        val firstPost = postRepository.findAll()[0]
        assertThat(firstPost.title).isEqualTo("제목입니다")
        assertThat(firstPost.content).isEqualTo("내용입니다")
    }

    @Test
    @DisplayName("글 단건 조회")
    fun `글 단건 조회`(){

        //given
        val createPost = Post(title = "foo", content = "bar")
        postRepository.save(createPost)

        createPost.id?.let {
            val post = postService.getPost(it)
            assertThat(post).isNotNull
            assertThat(post.title).isEqualTo("foo")
            assertThat(post.content).isEqualTo("bar")

        } ?: kotlin.run {
            Assertions.fail("실패한 테스트 케이스입니다")
        }

    }
}