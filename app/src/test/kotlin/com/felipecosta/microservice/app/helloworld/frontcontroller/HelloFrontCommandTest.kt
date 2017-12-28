package com.felipecosta.microservice.app.helloworld.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HelloFrontCommandTest {

    private val request = mockk<Request>()

    private val helloSparkFrontCommand = HelloFrontCommand().apply { init(request) }

    @Test
    fun whenProcessThenAssertResponse() {
        helloSparkFrontCommand.process()

        assertEquals(Response("Hello World from a server written Kotlin.", 200), helloSparkFrontCommand.response)
    }
}