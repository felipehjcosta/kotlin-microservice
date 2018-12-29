package com.felipecosta.microservice.server.renderer.impl

import com.felipecosta.microservice.server.renderer.Renderer
import java.io.StringWriter

class PebbleRenderer(val pebbleEngine: com.mitchellbosecke.pebble.PebbleEngine) : Renderer {
    override fun render(outputObject: Any, path: String) =
            with(StringWriter()) {
                val compiledTemplate = pebbleEngine.getTemplate(path)
                val context = transformOutputObjectIntoMap(outputObject)

                if (context.isEmpty()) {
                    compiledTemplate.evaluate(this)
                } else {
                    compiledTemplate.evaluate(this, context)
                }

                toString()
            }

    @Suppress("UNCHECKED_CAST")
    private fun transformOutputObjectIntoMap(outputObject: Any): Map<String, Any> = when (outputObject) {
        is Map<*, *> -> outputObject as Map<String, Any>
        else -> mapOf(outputObject.javaClass.simpleName.toLowerCase() to outputObject)
    }
}
