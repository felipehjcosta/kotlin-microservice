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

class ServerPutActionTest {

    val putHandlerCaptor = argumentCaptor<PutHandler<FrontCommand>>()

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

            +(map put "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).put(putHandlerCaptor.capture())

        assertEquals(1, putHandlerCaptor.allValues.size)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerPath() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).put(putHandlerCaptor.capture())

        assertEquals(PutPath("/"), putHandlerCaptor.firstValue.putPath)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerFrontCommand() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).put(putHandlerCaptor.capture())

        assertEquals(mockFrontCommand, putHandlerCaptor.firstValue.action())
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerDefaultRenderer() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).put(putHandlerCaptor.capture())

        assertTrue { DefaultRenderer::class.java.isAssignableFrom(putHandlerCaptor.firstValue.renderer.javaClass) }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerCustomRenderer() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand } with mockRenderer)
        }

        verify(mockServerHandler).put(putHandlerCaptor.capture())

        assertEquals(mockRenderer, putHandlerCaptor.firstValue.renderer)
    }

    @Test
    fun givenServerWithMultipleVerbsItShouldAssertHandlerMultipleTimes() {
        server {

            handler { mockServerHandler }

            +(map put "/" to { mockFrontCommand })
            +(map put "/hello" to { mockFrontCommand })
        }

        verify(mockServerHandler, times(2)).put(putHandlerCaptor.capture())

        assertEquals(2, putHandlerCaptor.allValues.size)
    }
}