package com.saechim.saechimlog.controller

import com.saechim.saechimlog.repository.PostRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
internal class PostControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val postRepository: PostRepository
) {

    @BeforeEach
    fun clean(){
        postRepository.deleteAll()
    }

    @Test
    @DisplayName("/posts post요청 테스트 컨트롤러")
    fun postTest() {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
            .content("{\"title\": \"제목입니다\",\"content\":\"내용입니다\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }



    @Test
    @DisplayName("/posts 요청시 title 값은 필수")
    fun postTest2() {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
            .content("{\"title\": \"\",\"content\":\"내용입니다\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty.run { MockMvcResultMatchers.jsonPath("$.code")
                .value("400") })
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty.run { MockMvcResultMatchers.jsonPath("$.message")
                .value("잘못된 요청입니다") })
            .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다")
    fun postTest3() {

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
            .content("{\"title\": \"제목입니다\",\"content\":\"내용입니다\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(MockMvcResultHandlers.print())

        assertThat(postRepository.count()).isEqualTo(1)

        val post = postRepository.findAll()[0]

        assertThat(post.title).isEqualTo("제목입니다")
        assertThat(post.content).isEqualTo("내용입니다")
        assertThat(post.title).isNotEqualTo("제목스")
    }
}