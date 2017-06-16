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

class ServerGetActionTest {

    val getHandlerCaptor = argumentCaptor<GetHandler<FrontCommand>>()

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

            +(map get "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).get(getHandlerCaptor.capture())

        assertEquals(1, getHandlerCaptor.allValues.size)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerPath() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).get(getHandlerCaptor.capture())

        assertEquals(GetPath("/"), getHandlerCaptor.firstValue.getPath)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerFrontCommand() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).get(getHandlerCaptor.capture())

        assertEquals(mockFrontCommand, getHandlerCaptor.firstValue.action())
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerDefaultRenderer() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).get(getHandlerCaptor.capture())

        assertTrue { DefaultRenderer::class.java.isAssignableFrom(getHandlerCaptor.firstValue.renderer.javaClass) }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerCustomRenderer() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand } with mockRenderer)
        }

        verify(mockServerHandler).get(getHandlerCaptor.capture())

        assertEquals(mockRenderer, getHandlerCaptor.firstValue.renderer)
    }

    @Test
    fun givenServerWithMultipleVerbsItShouldAssertHandlerMultipleTimes() {
        server {

            handler { mockServerHandler }

            +(map get "/" to { mockFrontCommand })
            +(map get "/hello" to { mockFrontCommand })
        }

        verify(mockServerHandler, times(2)).get(getHandlerCaptor.capture())

        assertEquals(2, getHandlerCaptor.allValues.size)
    }
}