package com.saechim.saechimlog.controller

import com.saechim.saechimlog.request.PostCreate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class PostController {

    val log: Logger = LoggerFactory.getLogger(PostController::class.java)

    /**
     * 테스트용 메서드들만 놓은 상태
     */

    @PostMapping("/posts")
    fun post(@RequestBody @Valid postCreate: PostCreate) : Map<String,String> {
        log.info("postCreate 값: {}",postCreate.toString())

        return mapOf()
    }

    @GetMapping("/posts")
    fun get() : String {
        return "Hello World"
    }
}