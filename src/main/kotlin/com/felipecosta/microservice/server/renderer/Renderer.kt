package com.felipecosta.microservice.server.renderer

interface Renderer {
    fun render(outputObject: Any, path: String): String
}