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

class ServerPostActionTest {

    val postHandlerCaptor = argumentCaptor<PostHandler<FrontCommand>>()

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

            +(map post "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).post(postHandlerCaptor.capture())

        assertEquals(1, postHandlerCaptor.allValues.size)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerPath() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).post(postHandlerCaptor.capture())

        assertEquals(PostPath("/"), postHandlerCaptor.firstValue.postPath)
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerFrontCommand() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).post(postHandlerCaptor.capture())

        assertEquals(mockFrontCommand, postHandlerCaptor.firstValue.action())
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerDefaultRenderer() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
        }

        verify(mockServerHandler).post(postHandlerCaptor.capture())

        assertTrue { DefaultRenderer::class.java.isAssignableFrom(postHandlerCaptor.firstValue.renderer.javaClass) }
    }

    @Test
    fun givenServerWithVerbItShouldAssertHandlerCustomRenderer() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand } with mockRenderer)
        }

        verify(mockServerHandler).post(postHandlerCaptor.capture())

        assertEquals(mockRenderer, postHandlerCaptor.firstValue.renderer)
    }

    @Test
    fun givenServerWithMultipleVerbsItShouldAssertHandlerMultipleTimes() {
        server {

            handler { mockServerHandler }

            +(map post "/" to { mockFrontCommand })
            +(map post "/hello" to { mockFrontCommand })
        }

        verify(mockServerHandler, times(2)).post(postHandlerCaptor.capture())

        assertEquals(2, postHandlerCaptor.allValues.size)
    }
}