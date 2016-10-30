package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer

interface FrontCommand {
    fun process()

    fun init(renderer: Renderer = DefaultRenderer())

    val output: String
}