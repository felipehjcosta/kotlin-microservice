package com.felipecosta.microservice.renderer.impl

import com.felipecosta.microservice.renderer.Renderer
import com.mitchellbosecke.pebble.PebbleEngine
import java.io.StringWriter

class PebbleRenderer(val pebbleEngine: PebbleEngine) : Renderer {
    override fun render(outputObject: Any, path: String): String {
        val compiledTemplate = pebbleEngine.getTemplate(path)

        val writer = StringWriter()
        val context = transformOutputObjectIntoMap(outputObject)

        if (context.isEmpty()) {
            compiledTemplate.evaluate(writer)
        } else {
            compiledTemplate.evaluate(writer, context)
        }
        return writer.toString()
    }

    @Suppress("UNCHECKED_CAST")
    private fun transformOutputObjectIntoMap(outputObject: Any): Map<String, Any> {
        return when (outputObject) {
            is Map<*, *> -> outputObject as Map<String, Any>
            else -> mapOf(outputObject.javaClass.simpleName.toLowerCase() to outputObject)
        }
    }
}