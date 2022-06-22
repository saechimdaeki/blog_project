package com.saechim.saechimlog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.repository.PostRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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
}