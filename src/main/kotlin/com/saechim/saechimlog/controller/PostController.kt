package com.saechim.saechimlog.controller

import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.service.PostService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class PostController(
    val postService: PostService
) {
    private val log: Logger = LoggerFactory.getLogger(PostController::class.java)

    @PostMapping("/posts")
    fun post(@RequestBody @Valid postCreate: PostCreate) : ResponseEntity<Any> {
        log.info("postCreate 값: {}",postCreate.toString())
        val write = postService.write(postCreate)

        val entityModel = EntityModel.of(write, linkTo<PostController> {
            WebMvcLinkBuilder.methodOn(PostController::class.java).post(postCreate)
        }.withSelfRel())

        return ResponseEntity.ok(entityModel)
    }

}