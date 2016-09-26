package com.felipecosta.microservice.renderer.impl

import com.felipecosta.microservice.renderer.Renderer
import java.io.File

class DefaultRenderer : Renderer {
    override fun render(outputObject: Any, path: String): String {
        return File(javaClass.classLoader.getResource(path).toURI()).readText()
    }
}