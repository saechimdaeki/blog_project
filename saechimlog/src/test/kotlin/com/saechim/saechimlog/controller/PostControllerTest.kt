package com.saechim.saechimlog.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
internal class PostControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @Test
    @DisplayName("/posts 요청시 Hello World print")
    fun test() {
        mockMvc.perform(get("/posts"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello World"))
            .andDo(print())
    }

    @Test
    @DisplayName("/posts post요청 테스트 컨트롤러")
    fun postTest() {
        mockMvc.perform(post("/posts")
            .content("{\"title\": \"제목입니다\",\"content\":\"내용입니다\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())
    }



    @Test
    @DisplayName("/posts 요청시 title 값은 필수")
    fun postTest2() {
        mockMvc.perform(post("/posts")
            .content("{\"title\": \"\",\"content\":\"내용입니다\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").isNotEmpty)
            .andDo(print())
    }
}