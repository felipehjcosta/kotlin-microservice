package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import io.mockk.mockk
import org.junit.After
import org.junit.Test
import kotlin.test.assertNotNull

class SpringBootServerHandlerTest {

    private val serverHandler = SpringBootServerHandler()

    private val mockFrontCommand = mockk<FrontCommand>()

    @After
    fun tearDown() {
        ServerUrlMappings.clear()
    }

    @Test
    fun givenHandlerWhenGetItShouldMapPath() {
        val path = GetPath("/")
        val action = { mockFrontCommand }
        serverHandler.get(GetHandler(path, action))

        assertNotNull(ServerUrlMappings[path])
    }

    @Test
    fun givenHandlerWhenPostItShouldMapPath() {
        val path = PostPath("/")
        val action = { mockFrontCommand }
        serverHandler.post(PostHandler(path, action))

        assertNotNull(ServerUrlMappings[path])
    }

    @Test
    fun givenHandlerWhenPutItShouldMapPath() {
        val path = PutPath("/")
        val action = { mockFrontCommand }
        serverHandler.put(PutHandler(path, action))

        assertNotNull(ServerUrlMappings[path])
    }

    @Test
    fun givenHandlerWhenDeleteItShouldMapPath() {
        val path = DeletePath("/")
        val action = { mockFrontCommand }
        serverHandler.delete(DeleteHandler(path, action))

        assertNotNull(ServerUrlMappings[path])
    }

}