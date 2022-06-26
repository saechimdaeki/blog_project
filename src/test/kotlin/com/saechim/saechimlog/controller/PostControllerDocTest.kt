package com.saechim.saechimlog.controller

import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.repository.PostRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
@SpringBootTest
@ExtendWith(RestDocumentationExtension::class)
class PostControllerDocTest(
    @Autowired
    val postRepository: PostRepository
) {

    lateinit var mockMvc : MockMvc

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext, restDocumentationContextProvider: RestDocumentationContextProvider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder?>(documentationConfiguration(restDocumentationContextProvider))
            .build()
    }

    @Test
    @DisplayName("글 단건 조회 테스트")
    fun `글 단건 조회 테스트`(){

        val post = Post(title = "foo", content = "bar")
        postRepository.save(post)

        mockMvc.perform(get("/posts/{postId}",1L)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk)
            .andDo(document("index"))
    }
}