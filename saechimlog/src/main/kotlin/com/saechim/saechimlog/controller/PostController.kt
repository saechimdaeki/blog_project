package com.saechim.saechimlog.controller

import com.saechim.saechimlog.request.PostCreate
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    val logger= LoggerFactory.getLogger(PostController::class.java)
    /**
     * 테스트용 메서드들만 놓은 상태
     */

    @PostMapping("/posts")
    fun post(@RequestBody postCreate: PostCreate) : String {
        logger.info("title = {}, content = {}",postCreate.title, postCreate.content)
        return "Hello World"
    }

    @GetMapping("/posts")
    fun get() : String {
        return "Hello World"
    }
}