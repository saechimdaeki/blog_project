package com.saechim.saechimlog.controller

import com.saechim.saechimlog.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice {

    val log: org.slf4j.Logger = LoggerFactory.getLogger(PostController::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun invalidRequestHandler(e:MethodArgumentNotValidException): ErrorResponse{
//        val fieldError = e.fieldError
//        fieldError?.let {
//            val field = fieldError.field
//            val defaultMessage = fieldError.defaultMessage ?: "에러메세지 없음"
//
//            val errorResponse = ErrorResponse("400", "잘못된 요청입니다")
//
//        }
        val errorResponse = ErrorResponse("400", "잘못된 요청입니다", mutableMapOf())
        e.fieldErrors.forEach { fieldError ->
            errorResponse.addValidation(fieldError.field,
                fieldError.defaultMessage ?: "없는 에러메시지")
        }
        return errorResponse
    }
}