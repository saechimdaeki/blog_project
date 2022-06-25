package com.saechim.saechimlog.controller

import com.saechim.saechimlog.dto.PostCreate
import com.saechim.saechimlog.dto.PostEdit
import com.saechim.saechimlog.dto.PostResponse
import com.saechim.saechimlog.dto.PostSearch
import com.saechim.saechimlog.service.PostService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
        postCreate.validate()

        val write = postService.write(postCreate)

        val entityModel = EntityModel.of(PostResponse.from(write), linkTo<PostController> {
            WebMvcLinkBuilder.methodOn(PostController::class.java).writePost(postCreate)
        }.withSelfRel(),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).getPost(write.id!!)
            }.withRel("seeDetail"),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).getList(PostSearch())
            }.withRel("listInfo"),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).editPost(write.id!!, PostEdit())
            }.withRel("editInfo"),

            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).deletePost(write.id!!)
            }.withRel("deletePost")
        )
        return ResponseEntity.ok(entityModel)
    }


    @GetMapping("/posts/{postId}")
    fun getPost(@PathVariable postId : Long) : ResponseEntity<Any>{
        val entityModel = EntityModel.of(postService.getPost(postId), linkTo<PostController> {
            WebMvcLinkBuilder.methodOn(PostController::class.java).getPost(postId)
        }.withSelfRel(),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).editPost(postId, PostEdit())
            }.withRel("editInfo"),

            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).deletePost(postId)
            }.withRel("deletePost")
        )
        return ResponseEntity.ok(entityModel)
    }

    @GetMapping("/posts")
    fun getList(@ModelAttribute postSearch: PostSearch): ResponseEntity<Any> {
        val collectionModel = CollectionModel.of(postService.getList(postSearch), linkTo<PostController> {
            WebMvcLinkBuilder.methodOn(PostController::class.java).getList(postSearch)
        }.withSelfRel())
        return ResponseEntity.ok(collectionModel)
    }

    @PatchMapping("/posts/{postId}")
    fun editPost(@PathVariable postId: Long, @RequestBody postEdit: PostEdit): ResponseEntity<Any> {
        val entityModel = EntityModel.of(
            postService.edit(postId, postEdit),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).editPost(postId, postEdit)
            }.withSelfRel(),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).getPost(postId)
            }.withRel("seeDetail"),

            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).deletePost(postId)
            }.withRel("deletePost")
        )
        return ResponseEntity.ok(entityModel)
    }

    @DeleteMapping("/posts/{postId}")
    fun deletePost(@PathVariable postId: Long) : ResponseEntity<Any>{
        val entityModel = EntityModel.of(
            postService.delete(postId),
            linkTo<PostController> {
                WebMvcLinkBuilder.methodOn(PostController::class.java).deletePost(postId)
            }.withSelfRel()
        )
        return ResponseEntity.ok(entityModel)
    }

}