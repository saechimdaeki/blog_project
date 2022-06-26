package com.saechim.saechimlog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.saechim.saechimlog.domain.Post
import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.repository.PostRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.saechimLog.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension::class)
class PostControllerDocTest(
    @Autowired
    val postRepository: PostRepository,

    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val objectMapper: ObjectMapper,
) {

    @Test
    @DisplayName("글 단건 조회 테스트")
    fun `글 단건 조회 테스트`(){

        val post = Post(title = "foo", content = "bar")
        postRepository.save(post)

        mockMvc.perform(get("/posts/{postId}", 1L)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk)
            .andDo(document("index",
                pathParameters(
                    parameterWithName("postId").description("게시글 ID")
                ),
                responseFields(
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("content").description("내용"),
                    fieldWithPath("_links.self.href").description("요청 uri 정보"),
                    fieldWithPath("_links.editInfo.href").description("글 수정 uri 정보"),
                    fieldWithPath("_links.deletePost.href").description("글 삭제 uri 정보")
                )
            ))
    }

    @Test
    @DisplayName("글 등록 테스트")
    fun `글 등록 테스트`() {
        val requestJson = objectMapper.writeValueAsString(PostCreate(title = "saechim", content = "내용입니다"))

        mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andDo(print())
            .andExpect(status().isOk)
            .andDo(document("index",
                requestFields(
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("content").description("내용")

                ),

                responseFields(
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("content").description("내용"),
                    fieldWithPath("_links.self.href").description("요청 uri 정보"),
                    fieldWithPath("_links.seeDetail.href").description("글 디테일 uri 정보"),
                    fieldWithPath("_links.listInfo.href").description("글 목록 uri 정보"),
                    fieldWithPath("_links.editInfo.href").description("글 수정 uri 정보"),
                    fieldWithPath("_links.deletePost.href").description("글 삭제 uri 정보")

                )
            ))
    }
}