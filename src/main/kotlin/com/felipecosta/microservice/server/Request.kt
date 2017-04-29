package com.felipecosta.microservice.server

class Request(wrappedRequest: spark.Request) {

    val params: Map<String, Array<String>> by lazy { wrappedRequest.queryMap().toMap() }

    val url: String  by lazy { wrappedRequest.url() }

    val host: String  by lazy { wrappedRequest.host() }

    val userAgent: String  by lazy { wrappedRequest.userAgent() }

}