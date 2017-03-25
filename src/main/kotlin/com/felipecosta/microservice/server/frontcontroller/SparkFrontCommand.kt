package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.renderer.Renderer
import spark.Request
import spark.Response

abstract class SparkFrontCommand : FrontCommand {

    private lateinit var renderer: Renderer

    override var output: String = ""

    protected lateinit var request: Request
    protected lateinit var response: Response

    override fun init(request: Request, response: Response, renderer: Renderer) {
        this.request = request
        this.response = response
        this.renderer = renderer
    }

    fun render(output: Any = emptyMap<String, Any>(), template: String = "", text: String = "") {
        this.output = if (text.isNotBlank()) text else renderer.render(output, template)
    }

}