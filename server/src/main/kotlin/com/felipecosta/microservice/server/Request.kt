package com.felipecosta.microservice.server

interface Request {
    val queryParams: Map<String, Array<String>>

    val routeParams: Map<String, String>

    val body: String

    val url: String

    val host: String

    val userAgent: String
}
