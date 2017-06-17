package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ServerDeleteActionTest {

    val deleteHandlerCaptor = argumentCaptor<DeleteHandler<FrontCommand>>()

    val mockFrontCommand = mock<FrontCommand>()

    val mockServerHandler = mock<ServerHandler>()

    val mockRenderer = mock<Renderer>()

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

        verify(mockServerHandler).delete(deleteHandlerCaptor.capture())

        assertEquals(1, deleteHandlerCaptor.allValues.size)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerPath() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).delete(deleteHandlerCaptor.capture())

        assertEquals(DeletePath("/"), deleteHandlerCaptor.firstValue.deletePath)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerFrontCommand() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).delete(deleteHandlerCaptor.capture())

        assertEquals(mockFrontCommand, deleteHandlerCaptor.firstValue.action())
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerDefaultRenderer() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).delete(deleteHandlerCaptor.capture())

        assertTrue { DefaultRenderer::class.java.isAssignableFrom(deleteHandlerCaptor.firstValue.renderer.javaClass) }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerCustomRenderer() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand } with mockRenderer)
        }

        verify(mockServerHandler).delete(deleteHandlerCaptor.capture())

        assertEquals(mockRenderer, deleteHandlerCaptor.firstValue.renderer)
    }

    @Test
    fun givenServerWithMultipleVerbsItShouldAssertHandlerMultipleTimes() {
        server {

            handler { mockServerHandler }

            +(map delete "/" to { mockFrontCommand })
            +(map delete "/hello" to { mockFrontCommand })
        }

        verify(mockServerHandler, times(2)).delete(deleteHandlerCaptor.capture())

        assertEquals(2, deleteHandlerCaptor.allValues.size)
    }
}