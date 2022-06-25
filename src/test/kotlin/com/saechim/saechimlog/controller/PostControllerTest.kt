package com.saechim.saechimlog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.dto.PostEdit
import com.saechim.saechimlog.repository.PostRepository
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
internal class PostControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val postRepository: PostRepository,
    @Autowired val objectMapper: ObjectMapper
) {

    @BeforeEach
    fun clean(){
        postRepository.deleteAll()
    }

    @Test
    @DisplayName("/posts post요청 테스트 컨트롤러")
    fun postTest() {

        val requestJson = objectMapper.writeValueAsString(PostCreate(title = "제목입니다", content = "내용입니다"))
        mockMvc.perform(post("/posts")
            .content(requestJson)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())
    }



    @Test
    @DisplayName("/posts 요청시 title 값은 필수")
    fun postTest2() {

        val requestJson = objectMapper.writeValueAsString(PostCreate(title = "", content = "내용입니다"))

        mockMvc.perform(post("/posts")
            .content(requestJson)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").isNotEmpty
                .run { jsonPath("$.code")
                .value("400") })
            .andExpect(jsonPath("$.message").isNotEmpty
                .run { jsonPath("$.message")
                .value("잘못된 요청입니다") })
            .andExpect(jsonPath("$.validation.title").isNotEmpty)
            .andDo(print())
    }


    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다")
    fun postTest3() {

        val requestJson = objectMapper.writeValueAsString(PostCreate(title = "제목입니다", content = "내용입니다"))

        //when
        mockMvc.perform(post("/posts")
            .content(requestJson)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())

        assertThat(postRepository.count()).isEqualTo(1)

        val post = postRepository.findAll()[0]

        assertThat(post.title).isEqualTo("제목입니다")
        assertThat(post.content).isEqualTo("내용입니다")
        assertThat(post.title).isNotEqualTo("제목스")
    }

    @Test
    @DisplayName("글 단건 조회")
    fun `글 단건 조회 테스트`(){
        //given
        val post = Post(title = "foo", content = "bar")
        postRepository.save(post)
        //when
        mockMvc.perform(get("/posts/{postId}",post.id)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("foo"))
            .andExpect(jsonPath("$.content").value("bar"))
            .andDo(print())
    }

    @Test
    @DisplayName("글 여러건 조회")
    fun `글 여러건 조회 테스트`(){
        //given
        val requestPosts = (0 until 30).map { Post(title = "saechim-$it", content = "content-$it") }
            .toList()

        postRepository.saveAll(requestPosts)


        //when
        /*
        *
        * Hateoas CollectionModel을 사용한다는걸 간과하지말자
        *   */
        mockMvc.perform(get("/posts?page=1&size=10")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$._embedded.postResponseList.length()",Matchers.`is`(10)))
            .andExpect(jsonPath("$._embedded.postResponseList[0].title").value("saechim-29"))
            .andExpect(jsonPath("$._embedded.postResponseList[0].content").value("content-29"))
            .andExpect(jsonPath("$._embedded.postResponseList[1].title").value("saechim-28"))
            .andExpect(jsonPath("$._embedded.postResponseList[1].content").value("content-28"))
            .andDo(print())
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다")
    fun `페이지 0조회 테스트`(){
        //given
        val requestPosts = (0 until 30).map { Post(title = "saechim-$it", content = "content-$it") }
            .toList()

        postRepository.saveAll(requestPosts)


        //when
        /*
        *
        * Hateoas CollectionModel을 사용한다는걸 간과하지말자
        *   */
        mockMvc.perform(get("/posts?page=0&size=10")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$._embedded.postResponseList.length()",Matchers.`is`(10)))
            .andExpect(jsonPath("$._embedded.postResponseList[0].title").value("saechim-29"))
            .andExpect(jsonPath("$._embedded.postResponseList[0].content").value("content-29"))
            .andExpect(jsonPath("$._embedded.postResponseList[1].title").value("saechim-28"))
            .andExpect(jsonPath("$._embedded.postResponseList[1].content").value("content-28"))
            .andDo(print())
    }


    @Test
    @DisplayName("글 제목 수정")
    fun `글 제목 수정`(){
        //given

        val createPost = Post(title = "saechim", content = "연봉 올리자")

        postRepository.save(createPost)

        //when
        val postEdit = PostEdit(title = "daeki")

        mockMvc.perform(patch("/posts/{postId}",createPost.id) //Patch / posts/{postId}
            .contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(postEdit)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("daeki"))
            .andExpect(jsonPath("$.content").value("연봉 올리자"))
            .andDo(print())
    }


    @Test
    @DisplayName("게시글 삭제")
    fun `게시글 삭제`(){
        //given

        val createPost = Post(title = "saechim", content = "연봉 올리자")

        postRepository.save(createPost)


        mockMvc.perform(delete("/posts/{postId}",createPost.id)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    fun `존재하지 않는 게시글 조회`(){

        mockMvc.perform(delete("/posts/{postId}",1L)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andDo(print())
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    fun `존재하지 않는 게시글 수정`(){

        val postEdit = PostEdit(title = "daeki")


        mockMvc.perform(patch("/posts/{postId}",1L)
            .contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(postEdit)))
            .andExpect(status().isNotFound)
            .andDo(print())
    }

    @Test
    @DisplayName("게시글 제목에 바보는 들어갈 수 없음.")
    fun `게시글 제목에 바보는 들어갈 수 없다`() {

        val requestJson = objectMapper.writeValueAsString(PostCreate(title = "제목 바보입니다", content = "내용입니다"))
        mockMvc.perform(post("/posts")
            .content(requestJson)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andDo(print())
    }
}