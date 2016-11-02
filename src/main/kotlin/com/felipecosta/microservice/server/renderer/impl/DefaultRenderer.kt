package com.felipecosta.microservice.server.renderer.impl

import com.felipecosta.microservice.server.renderer.Renderer

class DefaultRenderer : Renderer {
    override fun render(outputObject: Any, path: String): String = javaClass.
            classLoader.getResourceAsStream(path).reader().use { it.readText() }
}