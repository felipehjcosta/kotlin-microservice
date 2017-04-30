package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.renderer.Renderer
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertEquals
import org.mockito.Mockito.verifyZeroInteractions

class FrontCommandTest {

    val request: Request = mock()

    val renderer: Renderer = mock()

    val frontCommand: FrontCommand = mock(defaultAnswer = org.mockito.Mockito.CALLS_REAL_METHODS)

    @org.junit.Before
    fun setUp() {
        frontCommand.init(request)
    }

    @org.junit.Test
    fun whenRenderWithTemplateThenVerifyOutput() {
        frontCommand.init(request, renderer)
        com.nhaarman.mockito_kotlin.whenever(renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html")).thenReturn("Awesome return")

        val expectedOutput = "Awesome return"
        frontCommand.render(template = "views/hello_world_test.html")
        val actualOutput = frontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }

    @org.junit.Test
    fun whenRenderWithTextThenVerifyOutput() {
        val expectedOutput = "{\"value:\" \"Hello\"}"
        frontCommand.render(text = "{\"value:\" \"Hello\"}")
        val actualOutput = frontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }

    @org.junit.Test
    fun givenMockRendererWhenRenderWithTextThenVerifyOutput() {
        frontCommand.init(request, renderer)

        frontCommand.render(text = "{\"value:\" \"Hello\"}")
        verifyZeroInteractions(renderer)
    }
}