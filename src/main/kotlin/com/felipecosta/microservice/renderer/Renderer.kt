package com.felipecosta.microservice.renderer

interface Renderer {
    fun render(outputObject: Any, path: String): Any
}