package com.felipecosta.microservice.server

class SparkRequestAdapter(private val wrappedRequest: spark.Request) : Request {
    override val queryParams: Map<String, Array<String>> by lazy { wrappedRequest.queryMap().toMap() }

    override val routeParams: Map<String, String> by lazy { wrappedRequest.params() }

    override val body: String by lazy { wrappedRequest.body() }

    override val url: String  by lazy { wrappedRequest.url() }

    override val host: String  by lazy { wrappedRequest.host() }

    override val userAgent: String  by lazy { wrappedRequest.userAgent() }
}