package com.felipecosta.microservice.app.helloworld.frontcontroller

import com.felipecosta.microservice.server.Request
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import kotlin.test.assertEquals

class HelloFrontCommandTest {

    val request: Request = mock()

    lateinit var helloSparkFrontCommand: HelloFrontCommand

    @org.junit.Before
    fun setUp() {
        helloSparkFrontCommand = HelloFrontCommand().apply { init(request) }
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsHelloWorldMessage() {
        helloSparkFrontCommand.process()

        assertEquals("Hello World form server with Kotlin", helloSparkFrontCommand.output)
    }
}