package com.felipecosta.microservice.server.renderer.impl

import com.mitchellbosecke.pebble.PebbleEngine
import com.mitchellbosecke.pebble.template.PebbleTemplate
import com.nhaarman.mockito_kotlin.mock
import org.mockito.Matchers.any
import org.mockito.Matchers.eq
import java.io.StringWriter

class PebbleRendererTest {

    var pebbleEngine: PebbleEngine = mock()

    var pebbleTemplate: PebbleTemplate = mock()

    lateinit var pebbleRenderer: PebbleRenderer

    @org.junit.Before
    fun setUp() {
        pebbleRenderer = PebbleRenderer(pebbleEngine)
    }

    @org.junit.Test
    fun givenOutputObjectWithTemplateWhenRenderThenAssertOutputText() {
        val outputObject = OutputObject("title", "content")

        com.nhaarman.mockito_kotlin.whenever(pebbleEngine.getTemplate(eq("index.html"))).thenReturn(pebbleTemplate)
        com.nhaarman.mockito_kotlin.whenever(pebbleTemplate.evaluate(any(StringWriter::class.java), eq(mapOf("outputobject" to outputObject)))).then {
            val stringWriter = it.arguments[0] as StringWriter
            stringWriter.append("awesome text")
        }

        val output = pebbleRenderer.render(outputObject, "index.html")

        kotlin.test.assertEquals("awesome text", output)
    }

    @org.junit.Test
    fun givenMapWithTemplateWhenRenderThenAssertOutputText() {
        val outputMap = mapOf("title" to "My First Website", "content" to "My Interesting Content")

        com.nhaarman.mockito_kotlin.whenever(pebbleEngine.getTemplate(eq("index.html"))).thenReturn(pebbleTemplate)
        com.nhaarman.mockito_kotlin.whenever(pebbleTemplate.evaluate(any(StringWriter::class.java), eq(outputMap))).then {
            val stringWriter = it.arguments[0] as StringWriter
            stringWriter.append("awesome text")
        }

        val output = pebbleRenderer.render(outputMap, "index.html")

        kotlin.test.assertEquals("awesome text", output)
    }

    @org.junit.Test
    fun givenEmptyMapWithTemplateWhenRenderThenAssertOutputText() {
        val outputObject = emptyMap<String, Any>()

        com.nhaarman.mockito_kotlin.whenever(pebbleEngine.getTemplate(eq("index.html"))).thenReturn(pebbleTemplate)
        com.nhaarman.mockito_kotlin.whenever(pebbleTemplate.evaluate(any(StringWriter::class.java))).then {
            val stringWriter = it.arguments[0] as StringWriter
            stringWriter.append("awesome text")
        }

        val output = pebbleRenderer.render(outputObject, "index.html")

        kotlin.test.assertEquals("awesome text", output)
    }

    class OutputObject(val title: String, val content: String)

}