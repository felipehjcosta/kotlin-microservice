package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer

abstract class FrontCommand {

    private lateinit var renderer: Renderer

    var output: String = ""

    protected lateinit var request: Request

    fun init(request: Request, renderer: Renderer = DefaultRenderer()) {
        this.request = request
        this.renderer = renderer
    }

    fun render(output: Any = emptyMap<String, Any>(), template: String = "", text: String = "") {
        this.output = if (text.isNotBlank()) text else renderer.render(output, template)
    }

    abstract fun process()

}