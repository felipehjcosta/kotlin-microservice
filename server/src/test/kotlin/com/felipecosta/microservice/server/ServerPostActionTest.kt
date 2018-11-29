package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class ServerPostActionTest {

    private val postHandlerSlotCaptor = slot<PostHandler<FrontCommand>>()

    private val mockFrontCommand = mockk<FrontCommand>(relaxed = true)

    private val mockServerHandler = mockk<ServerHandler>(relaxed = true)

    private val mockRenderer = mockk<Renderer>(relaxed = true)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun givenServerItShouldAssertEmptyServerHandler() {

        val serviceHandler = server {
        }.serverHandler

        assertTrue { EmptyServerHandler::class.java.isAssignableFrom(serviceHandler.javaClass) }
    }

    @Test
    fun givenServerWithHandlerItShouldAssertHandler() {
        val serviceHandler = server {
            handler { mockServerHandler }
        }.serverHandler

        assertEquals(mockServerHandler, serviceHandler)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandler() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.post(capture(postHandlerSlotCaptor)) }

        assertTrue { postHandlerSlotCaptor.isCaptured }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerPath() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.post(capture(postHandlerSlotCaptor)) }

        assertEquals(PostPath("/"), postHandlerSlotCaptor.captured.postPath)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerFrontCommand() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.post(capture(postHandlerSlotCaptor)) }

        assertEquals(mockFrontCommand, postHandlerSlotCaptor.captured.action())
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerDefaultRenderer() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.post(capture(postHandlerSlotCaptor)) }

        assertTrue { DefaultRenderer::class.java.isAssignableFrom(postHandlerSlotCaptor.captured.renderer.javaClass) }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerCustomRenderer() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand } with mockRenderer)
        }

        verify { mockServerHandler.post(capture(postHandlerSlotCaptor)) }

        assertEquals(mockRenderer, postHandlerSlotCaptor.captured.renderer)
    }

    @Test
    fun givenServerWithMultipleVerbsItShouldAssertHandlerMultipleTimes() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
            +(map post "/hello" to { mockFrontCommand })
        }

        verify(exactly = 2) { mockServerHandler.post(capture(postHandlerSlotCaptor)) }
    }
}