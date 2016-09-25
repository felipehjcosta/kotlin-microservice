package com.felipecosta.microservice.frontcontroller

import com.felipecosta.microservice.renderer.Renderer
import com.felipecosta.microservice.renderer.impl.DefaultRenderer

interface FrontCommand {
    fun process()

    fun init(renderer: Renderer = DefaultRenderer())

    val output: Any
}