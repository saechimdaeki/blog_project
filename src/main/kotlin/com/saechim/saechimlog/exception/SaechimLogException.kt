package com.saechim.saechimlog.exception

abstract class SaechimLogException : RuntimeException {

    val validation = mutableMapOf<String,String>()


    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(message: String) : super(message)


    abstract fun getStatusCode() : Int

    fun addValidation(fieldName:String , message: String){
        validation[fieldName] = message
    }
}