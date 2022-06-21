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
    fun post(@RequestBody @Valid postCreate: PostCreate, bindingResult: BindingResult) : Map<String,String> {
        log.info("postCreate 값: {}",postCreate.toString())
        if(bindingResult.hasErrors()){
            val fieldErrors = bindingResult.fieldErrors
            val firstFieldError = fieldErrors[0]
            val fieldName = firstFieldError.field
            val errorMessage = firstFieldError.defaultMessage ?: "메세지 없어요"
            val error = mutableMapOf<String,String>()
            error[fieldName] = errorMessage
            return error
        }
        return mapOf()
    }

    @GetMapping("/posts")
    fun get() : String {
        return "Hello World"
    }
}