package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
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
    fun whenRenderWithTemplateThenVerifyResponse() {
        frontCommand.init(request, renderer)
        com.nhaarman.mockito_kotlin.whenever(renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html")).thenReturn("Awesome return")

        val expectedResponse = Response("Awesome return", 200)
        frontCommand.render(template = "views/hello_world_test.html")
        val actualResponse = frontCommand.response
        assertEquals(expectedResponse, actualResponse)
    }

    @org.junit.Test
    fun whenRenderWithTextThenVerifyResponse() {
        val expectedResponse = Response("{\"value:\" \"Hello\"}", 200)
        frontCommand.render(text = "{\"value:\" \"Hello\"}")
        val actualResponse = frontCommand.response
        assertEquals(expectedResponse, actualResponse)
    }

    @org.junit.Test
    fun givenMockRendererWhenRenderWithTextThenVerifyResponse() {
        frontCommand.init(request, renderer)

        frontCommand.render(text = "{\"value:\" \"Hello\"}")
        verifyZeroInteractions(renderer)
    }
}