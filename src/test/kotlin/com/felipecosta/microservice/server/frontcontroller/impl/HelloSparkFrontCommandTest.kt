package com.felipecosta.microservice.server.frontcontroller.impl

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import spark.Request
import spark.Response
import kotlin.test.assertEquals

class HelloSparkFrontCommandTest {

    val request: Request = mock()

    val response: Response = mock()

    lateinit var helloSparkFrontCommand: HelloSparkFrontCommand

    @org.junit.Before
    fun setUp() {
        helloSparkFrontCommand = HelloSparkFrontCommand()
        helloSparkFrontCommand.init(request, response)
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsHelloWorldMessage() {
        helloSparkFrontCommand.process()

        assertEquals("Hello World form Spark with Kotlin", helloSparkFrontCommand.output)
    }
}