package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.renderer.Renderer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verifyZeroInteractions

class FrontCommandTest {

    val request: Request = mock()

    val renderer: Renderer = mock()

    val frontCommand: FrontCommand = mock(Mockito.CALLS_REAL_METHODS)

    @Before
    fun setUp() {
        frontCommand.init(request)
    }

    @Test
    fun whenRenderWithTemplateThenVerifyOutput() {
        frontCommand.init(request, renderer)
        whenever(renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html")).thenReturn("Awesome return")

        val expectedOutput = "Awesome return"
        frontCommand.render(template = "views/hello_world_test.html")
        val actualOutput = frontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun whenRenderWithTextThenVerifyOutput() {
        val expectedOutput = "{\"value:\" \"Hello\"}"
        frontCommand.render(text = "{\"value:\" \"Hello\"}")
        val actualOutput = frontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun givenMockRendererWhenRenderWithTextThenVerifyOutput() {
        frontCommand.init(request, renderer)

        frontCommand.render(text = "{\"value:\" \"Hello\"}")
        verifyZeroInteractions(renderer)
    }
}