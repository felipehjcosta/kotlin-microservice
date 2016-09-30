package com.felipecosta.microservice.frontcontroller.impl

import com.felipecosta.microservice.utils.mock
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.verify
import spark.Request
import spark.Response
import kotlin.test.assertEquals

class HelloSparkFrontCommandTest {

    val request: Request = mock()

    val response: Response = mock()

    var helloSparkFrontCommand: HelloSparkFrontCommand? = null

    @org.junit.Before
    fun setUp() {
        helloSparkFrontCommand = HelloSparkFrontCommand(request, response)
        helloSparkFrontCommand?.init()
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsHelloWorldMessage() {
        helloSparkFrontCommand?.process()

        assertEquals("Hello World form Spark with Kotlin", helloSparkFrontCommand?.output)
    }
}