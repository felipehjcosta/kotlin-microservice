package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.renderer.Renderer
import spark.Request
import spark.Response

abstract class SparkFrontCommand(val request: Request, val response: Response) : FrontCommand {

    private var renderer: Renderer? = null

    override var output: String = ""

    override fun init(renderer: Renderer) {
        this.renderer = renderer
    }

    fun render(output: Any = emptyMap<String, Any>(), template: String = "", text: String = "") {
        this.output = if (text.isNotBlank()) text else renderer?.render(output, template) ?: ""
    }

}