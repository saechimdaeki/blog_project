package com.saechim.saechimlog.response

data class ErrorResponse(
    val code: String ,
    val message : String,
    val validation :MutableMap<String,String>
    ) {
    fun addValidation(field: String, defaultMessage: String) {
        validation[field] = defaultMessage
    }
}

