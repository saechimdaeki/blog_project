package com.saechim.saechimlog.exception

/**
 * 404
 */
class PostNotFoundException : SaechimLogException {

    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor() : super(MESSAGE)

    companion object {
        const val MESSAGE = "존재하지 않는 글입니다"
    }

    override fun getStatusCode(): Int {
        return 404
    }
}
