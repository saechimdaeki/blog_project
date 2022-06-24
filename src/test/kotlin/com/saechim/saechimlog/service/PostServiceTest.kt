package com.saechim.saechimlog.service

import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.dto.PostSearch
import com.saechim.saechimlog.repository.PostRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.awt.print.Pageable
import java.util.stream.IntStream

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

    @Test
    @DisplayName("글 제목이 10글자 이상일 때 글 단건 조회 ")
    fun `글 단건 조회 제목이 10글자 이상`(){

        //given
        val createPost = Post(title = "1234567891012345", content = "bar")
        postRepository.save(createPost)



        createPost.id?.let {
            val post = postService.getPost(it)
            assertThat(post).isNotNull
            assertThat(post.title).isEqualTo("1234567891")
            assertThat(post.content).isEqualTo("bar")

        } ?: kotlin.run {
            Assertions.fail("실패한 테스트 케이스입니다")
        }

    }

    @Test
    @DisplayName("글 여러건 조회")
    fun `글 여러건 조회`(){

        val createPost = Post(title = "foo", content = "bar")

        val createPost2 = Post(title = "foo2", content = "bar2")
        postRepository.saveAll(listOf(createPost,createPost2))

        val list = postService.getList(PostSearch(page = 1, size = 10))
        //when

        //then
        assertThat(list).isNotEmpty

        assertThat(list.size).isEqualTo(2)

    }



    @Test
    @DisplayName("글 1페이지 조회")
    fun `글 1페이지 조회`(){

        val requestPosts = (0 until 30).map { Post(title = "saechim-$it", content = "content-$it") }
            .toList()

        postRepository.saveAll(requestPosts)

        //when
        val list = postService.getList(PostSearch(page = 1, size = 10))

        //then
        assertThat(list).isNotEmpty

        assertThat(list.size).isEqualTo(10)
        assertThat("saechim-29").isEqualTo(list[0].title)

    }



}