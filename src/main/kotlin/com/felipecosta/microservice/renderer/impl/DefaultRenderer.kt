package com.felipecosta.microservice.renderer.impl

import com.felipecosta.microservice.renderer.Renderer
import java.io.File

class DefaultRenderer : Renderer {
    override fun render(outputObject: Any, path: String): String {
        return javaClass.classLoader.getResourceAsStream(path).reader().readText()
    }
}