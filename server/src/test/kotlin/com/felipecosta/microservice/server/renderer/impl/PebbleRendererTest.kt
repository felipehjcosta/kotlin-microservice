package com.felipecosta.microservice.server.renderer.impl

import com.mitchellbosecke.pebble.PebbleEngine
import com.mitchellbosecke.pebble.template.PebbleTemplate
import io.mockk.every
import io.mockk.mockk
import java.io.StringWriter

class PebbleRendererTest {

    private var pebbleEngine = mockk<PebbleEngine>()

    private val pebbleTemplate = mockk<PebbleTemplate>()

    private val pebbleRenderer = PebbleRenderer(pebbleEngine)

    @org.junit.Test
    fun givenOutputObjectWithTemplateWhenRenderThenAssertOutputText() {
        val outputObject = OutputObject("title", "content")

        every { pebbleEngine.getTemplate("index.html") } returns pebbleTemplate
        every { pebbleTemplate.evaluate(any(), mapOf("outputobject" to outputObject)) } answers {
            (args[0] as StringWriter).append("awesome text")
        }

        val output = pebbleRenderer.render(outputObject, "index.html")

        kotlin.test.assertEquals("awesome text", output)
    }

    @org.junit.Test
    fun givenMapWithTemplateWhenRenderThenAssertOutputText() {
        val outputMap = mapOf("title" to "My First Website", "content" to "My Interesting Content")

        every { pebbleEngine.getTemplate("index.html") } returns pebbleTemplate
        every { pebbleTemplate.evaluate(any(), outputMap) } answers { (args[0] as StringWriter).append("awesome text") }

        val output = pebbleRenderer.render(outputMap, "index.html")

        kotlin.test.assertEquals("awesome text", output)
    }

    @org.junit.Test
    fun givenEmptyMapWithTemplateWhenRenderThenAssertOutputText() {
        val outputObject = emptyMap<String, Any>()

        every { pebbleEngine.getTemplate("index.html") } returns pebbleTemplate
        every { pebbleTemplate.evaluate(any()) } answers { (args[0] as StringWriter).append("awesome text") }

        val output = pebbleRenderer.render(outputObject, "index.html")

        kotlin.test.assertEquals("awesome text", output)
    }

    class OutputObject(val title: String, val content: String)

}