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

class ServerGetActionTest {

    private val getHandlerSlotCaptor = slot<GetHandler<FrontCommand>>()

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

            +(map get "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.get(capture(getHandlerSlotCaptor)) }

        assertTrue { getHandlerSlotCaptor.isCaptured }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerPath() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.get(capture(getHandlerSlotCaptor)) }

        assertEquals(GetPath("/"), getHandlerSlotCaptor.captured.getPath)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerFrontCommand() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.get(capture(getHandlerSlotCaptor)) }

        assertEquals(mockFrontCommand, getHandlerSlotCaptor.captured.action())
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerDefaultRenderer() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand })
        }

        verify { mockServerHandler.get(capture(getHandlerSlotCaptor)) }

        assertTrue { DefaultRenderer::class.java.isAssignableFrom(getHandlerSlotCaptor.captured.renderer.javaClass) }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerCustomRenderer() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand } with mockRenderer)
        }

        verify { mockServerHandler.get(capture(getHandlerSlotCaptor)) }

        assertEquals(mockRenderer, getHandlerSlotCaptor.captured.renderer)
    }

    @Test
    fun givenServerWithMultipleVerbsItShouldAssertHandlerMultipleTimes() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand })
            +(map get "/hello" to { mockFrontCommand })
        }

        verify(exactly = 2) { mockServerHandler.get(capture(getHandlerSlotCaptor)) }
    }
}