package com.felipecosta.microservice.server

interface Request {
    val params: Map<String, Array<String>>

    val url: String

    val host: String

    val userAgent: String
}