package com.saechim.saechimlog.response

import com.fasterxml.jackson.annotation.JsonInclude

//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
class ErrorResponse(
    val code: String ,
    val message : String,
    val validation :MutableMap<String,String>
    ) {
    fun addValidation(field: String, defaultMessage: String) {
        validation[field] = defaultMessage
    }
}

