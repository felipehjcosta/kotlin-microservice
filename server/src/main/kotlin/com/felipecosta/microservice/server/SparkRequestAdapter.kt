package com.felipecosta.microservice.server

class SparkRequestAdapter(wrappedRequest: spark.Request) : Request {

    override val params: Map<String, Array<String>> by lazy { wrappedRequest.queryMap().toMap() }

    override val url: String  by lazy { wrappedRequest.url() }

    override val host: String  by lazy { wrappedRequest.host() }

    override val userAgent: String  by lazy { wrappedRequest.userAgent() }
}