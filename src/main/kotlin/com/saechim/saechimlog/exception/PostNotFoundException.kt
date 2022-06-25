package com.saechim.saechimlog.exception

class PostNotFoundException : RuntimeException {

    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor() : super(MESSAGE)

    companion object {
        const val MESSAGE = "존재하지 않는 글입니다"
    }
}
