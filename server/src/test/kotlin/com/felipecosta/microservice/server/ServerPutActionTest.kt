package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ServerPutActionTest {

    private val putHandlerSlotCaptor = slot<PutHandler<FrontCommand>>()

    private val mockFrontCommand = mockk<FrontCommand>(relaxed = true)

    private val mockServerHandler = mockk<ServerHandler>(relaxed = true)

    private val mockRenderer = mockk<Renderer>(relaxed = true)

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

            +(map put "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.put(capture(putHandlerSlotCaptor)) }

        assertTrue { putHandlerSlotCaptor.isCaptured }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerPath() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.put(capture(putHandlerSlotCaptor)) }

        assertEquals(PutPath("/"), putHandlerSlotCaptor.captured.putPath)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerFrontCommand() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.put(capture(putHandlerSlotCaptor)) }

        assertEquals(mockFrontCommand, putHandlerSlotCaptor.captured.action())
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerDefaultRenderer() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.put(capture(putHandlerSlotCaptor)) }

        assertTrue { DefaultRenderer::class.java.isAssignableFrom(putHandlerSlotCaptor.captured.renderer.javaClass) }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerCustomRenderer() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand } with mockRenderer)
        }

        verify { mockServerHandler.put(capture(putHandlerSlotCaptor)) }

        assertEquals(mockRenderer, putHandlerSlotCaptor.captured.renderer)
    }

    @Test
    fun givenServerWithMultipleVerbsItShouldAssertHandlerMultipleTimes() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand })
            +(map put "/hello" to { mockFrontCommand })
        }

        verify(exactly = 2) { mockServerHandler.put(capture(putHandlerSlotCaptor)) }
    }
}