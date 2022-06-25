package com.saechim.saechimlog.exception

/**
 * 400
 */
class InvalidRequest() : SaechimLogException(MESSAGE) {


    companion object {
        const val MESSAGE = "잘못된 요청입니다"
    }

    constructor(fieldName:String , message : String) : this() {
        addValidation(fieldName,message)
    }

    override fun getStatusCode():Int{
        return 400
    }
}