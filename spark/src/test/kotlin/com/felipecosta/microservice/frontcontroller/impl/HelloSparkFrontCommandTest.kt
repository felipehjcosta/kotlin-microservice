package com.felipecosta.microservice.frontcontroller.impl

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import spark.Request
import spark.Response

class HelloSparkFrontCommandTest {

    val request: Request = mock()

    val response: Response = mock()

    var helloSparkFrontCommand: HelloSparkFrontCommand? = null

    @org.junit.Before
    fun setUp() {
        helloSparkFrontCommand = HelloSparkFrontCommand(request, response)
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsHelloWorldMessage() {
        helloSparkFrontCommand?.process()

        verify(response).body(eq("Hello World form Spark Front Command"))
    }
}