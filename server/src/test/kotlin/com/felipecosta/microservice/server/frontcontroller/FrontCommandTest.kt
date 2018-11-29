package com.felipecosta.microservice.server.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import com.felipecosta.microservice.server.renderer.Renderer
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class FrontCommandTest {

    private val request = mockk<Request>()

    private val renderer = mockk<Renderer>()

    private val frontCommand = spyk<FrontCommand>().apply { init(request) }

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun whenRenderWithTemplateThenVerifyResponse() {
        frontCommand.init(request, renderer)
        every { renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html") } returns "Awesome return"

        val expectedResponse = Response("Awesome return", 200)
        frontCommand.render(template = "views/hello_world_test.html")
        val actualResponse = frontCommand.response
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun whenRenderWithTextThenVerifyResponse() {
        val expectedResponse = Response("{\"value:\" \"Hello\"}", 200)
        frontCommand.render(text = "{\"value:\" \"Hello\"}")
        val actualResponse = frontCommand.response
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun givenMockRendererWhenRenderWithTextThenVerifyResponse() {
        frontCommand.init(request, renderer)

        frontCommand.render(text = "{\"value:\" \"Hello\"}")
        verify { renderer wasNot Called }
    }
}