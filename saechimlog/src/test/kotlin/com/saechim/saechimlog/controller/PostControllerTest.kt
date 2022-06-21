package com.saechim.saechimlog.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
internal class PostControllerTest(
    @Autowired val mockMvc: MockMvc
){

    @Test
    @DisplayName("/posts 요청시 Hello World print")
    fun test(){
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk)
                .andExpect(content().string("Hello World"))
                .andDo(print())
    }
}