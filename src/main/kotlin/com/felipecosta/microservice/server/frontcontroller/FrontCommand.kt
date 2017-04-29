package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer
import spark.Response

interface FrontCommand {
    fun process()

    fun init(request: Request, response: Response, renderer: Renderer = DefaultRenderer())

    val output: String
}