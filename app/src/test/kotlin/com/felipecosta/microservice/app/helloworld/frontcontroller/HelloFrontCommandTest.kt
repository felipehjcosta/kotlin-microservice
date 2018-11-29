package com.felipecosta.microservice.app.helloworld.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class HelloFrontCommandTest {

    private val request = mockk<Request>()

    private val helloSparkFrontCommand = HelloFrontCommand().apply { init(request) }

    @BeforeEach
    fun setUp() {
        clearMocks(request)
    }

    @Test
    fun whenProcessThenAssertResponse() {
        helloSparkFrontCommand.process()

        assertEquals(Response("Hello World from a server written Kotlin.", 200), helloSparkFrontCommand.response)
    }
}