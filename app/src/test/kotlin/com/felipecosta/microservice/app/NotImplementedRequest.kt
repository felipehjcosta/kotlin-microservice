package com.felipecosta.microservice.app

import com.felipecosta.microservice.server.Request

class NotImplementedRequest : Request {
    override val queryParams: Map<String, Array<String>>
        get() = TODO("not implemented")
    override val routeParams: Map<String, String>
        get() = TODO("not implemented")
    override val body: String
        get() = TODO("not implemented")
    override val url: String
        get() = TODO("not implemented")
    override val host: String
        get() = TODO("not implemented")
    override val userAgent: String
        get() = TODO("not implemented")
}