package com.felipecosta.microservice.frontcontroller

import com.felipecosta.microservice.renderer.Renderer
import spark.Request
import spark.Response

abstract class SparkFrontCommand(val request: Request, val response: Response) : FrontCommand {

    private var renderer: Renderer? = null

    override var output: Any = ""

    override fun init(renderer: Renderer) {
        this.renderer = renderer
    }

    fun render(output: Any = emptyMap<String, Any>(), template: String) {
        this.output = renderer?.render(output, template) ?: ""
    }

}