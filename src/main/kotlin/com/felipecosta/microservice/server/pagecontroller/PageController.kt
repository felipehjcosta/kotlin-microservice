package com.felipecosta.microservice.server.pagecontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer

abstract class PageController(private val renderer: Renderer = DefaultRenderer()) {

    var output: String = ""

    abstract fun doGet(request: Request)

    fun render(output: Any = emptyMap<String, Any>(), template: String = "", text: String = "") {
        this.output = if (text.isNotBlank()) text else renderer.render(output, template)
    }

}