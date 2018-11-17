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

class ServerDeleteActionTest {

    private val deleteHandlerSlotCaptor = slot<DeleteHandler<FrontCommand>>()

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

            +(map delete "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.delete(capture(deleteHandlerSlotCaptor)) }

        assertTrue { deleteHandlerSlotCaptor.isCaptured }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerPath() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.delete(capture(deleteHandlerSlotCaptor)) }

        assertEquals(DeletePath("/"), deleteHandlerSlotCaptor.captured.deletePath)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerFrontCommand() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.delete(capture(deleteHandlerSlotCaptor)) }

        assertEquals(mockFrontCommand, deleteHandlerSlotCaptor.captured.action())
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerDefaultRenderer() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.delete(capture(deleteHandlerSlotCaptor)) }

        assertTrue { DefaultRenderer::class.java.isAssignableFrom(deleteHandlerSlotCaptor.captured.renderer.javaClass) }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerCustomRenderer() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand } with mockRenderer)
        }

        verify { mockServerHandler.delete(capture(deleteHandlerSlotCaptor)) }

        assertEquals(mockRenderer, deleteHandlerSlotCaptor.captured.renderer)
    }

    @Test
    fun givenServerWithMultipleVerbsItShouldAssertHandlerMultipleTimes() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand })
            +(map delete "/hello" to { mockFrontCommand })
        }

        verify(exactly = 2) { mockServerHandler.delete(capture(deleteHandlerSlotCaptor)) }
    }
}