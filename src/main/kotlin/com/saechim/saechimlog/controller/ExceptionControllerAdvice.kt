package com.saechim.saechimlog.controller

import com.saechim.saechimlog.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun invalidRequestHandler(e:MethodArgumentNotValidException): ErrorResponse{

        val errorResponse = ErrorResponse("400", "잘못된 요청입니다", mutableMapOf())
        e.fieldErrors.forEach { fieldError ->
            errorResponse.addValidation(fieldError.field,
                fieldError.defaultMessage ?: "없는 에러메시지")
        }
        return errorResponse
    }
}