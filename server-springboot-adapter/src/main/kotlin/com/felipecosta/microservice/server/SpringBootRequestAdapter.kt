package com.felipecosta.microservice.server

import javax.servlet.http.HttpServletRequest

class SpringBootRequestAdapter(private val mockHttpServletRequest: HttpServletRequest) : Request {

    override val queryParams: Map<String, Array<String>> by lazy { mockHttpServletRequest.parameterMap ?: emptyMap() }

    override val routeParams: Map<String, String>
        get() = TODO("not implemented")

    override val body: String by lazy { mockHttpServletRequest.reader.use { it.readText() } }

    override val url: String by lazy { mockHttpServletRequest.requestURL.toString() }

    override val host: String by lazy { "${mockHttpServletRequest.scheme}://${mockHttpServletRequest.serverName}:${mockHttpServletRequest.serverPort}" }

    override val userAgent: String by lazy { mockHttpServletRequest.getHeader("User-Agent") ?: "" }
}