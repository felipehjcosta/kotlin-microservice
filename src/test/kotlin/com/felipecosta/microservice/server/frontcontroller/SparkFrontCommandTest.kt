package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.utils.mock
import com.felipecosta.microservice.utils.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verifyZeroInteractions
import spark.Request
import spark.Response

class SparkFrontCommandTest {

    val request: Request = mock()

    val response: Response = mock()

    val renderer: Renderer = mock()

    val sparkFrontCommand: SparkFrontCommand = mock(Mockito.CALLS_REAL_METHODS)

    @Before
    fun setUp() {
        sparkFrontCommand.init(request, response)
    }

    @Test
    fun whenRenderWithTemplateThenVerifyOutput() {
        sparkFrontCommand.init(request, response, renderer)
        whenever(renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html")).thenReturn("Awesome return")

        val expectedOutput = "Awesome return"
        sparkFrontCommand.render(template = "views/hello_world_test.html")
        val actualOutput = sparkFrontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun whenRenderWithTextThenVerifyOutput() {
        val expectedOutput = "{\"value:\" \"Hello\"}"
        sparkFrontCommand.render(text = "{\"value:\" \"Hello\"}")
        val actualOutput = sparkFrontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun givenMockRendererWhenRenderWithTextThenVerifyOutput() {
        sparkFrontCommand.init(request, response, renderer)

        sparkFrontCommand.render(text = "{\"value:\" \"Hello\"}")
        verifyZeroInteractions(renderer)
    }
}