package com.saechim.saechimlog.controller

import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.dto.PostResponse
import com.saechim.saechimlog.service.PostService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class PostController(
    val postService: PostService
) {
    private val log: Logger = LoggerFactory.getLogger(PostController::class.java)

    /**
     * /posts -> 글 전체 조회(검색 + 페이징)
     * /posts/{postId} -> 글 한개만 조회
     */

    @PostMapping("/posts")
    fun writePost(@RequestBody @Valid postCreate: PostCreate) : ResponseEntity<Any> {
        log.info("postCreate 값: {}",postCreate.toString())
        val write = postService.write(postCreate)

        val entityModel = EntityModel.of(PostResponse.from(write), linkTo<PostController> {
            WebMvcLinkBuilder.methodOn(PostController::class.java).writePost(postCreate)
        }.withSelfRel(),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).getPost(write.id!!)
            }.withRel("seeDetail"),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).getList(Pageable.ofSize(4))
            }.withRel("listInfo")
        )
        return ResponseEntity.ok(entityModel)
    }


    @GetMapping("/posts/{postId}")
    fun getPost(@PathVariable postId : Long) : ResponseEntity<Any>{
        val entityModel = EntityModel.of(postService.getPost(postId), linkTo<PostController> {
            WebMvcLinkBuilder.methodOn(PostController::class.java).getPost(postId)
        }.withSelfRel())
        return ResponseEntity.ok(entityModel)
    }

    @GetMapping("/posts")
    fun getList(page: Pageable): ResponseEntity<Any> {
        val collectionModel = CollectionModel.of(postService.getList(page), linkTo<PostController> {
            WebMvcLinkBuilder.methodOn(PostController::class.java).getList(page)
        }.withSelfRel())
        return ResponseEntity.ok(collectionModel)
    }

}