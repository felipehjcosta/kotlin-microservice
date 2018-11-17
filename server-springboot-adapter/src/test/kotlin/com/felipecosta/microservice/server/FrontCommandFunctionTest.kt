package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import org.junit.Test
import kotlin.test.assertEquals

class FrontCommandFunctionTest {

    private val spiedFrontCommand = spyk<FrontCommand>()

    private val mockRequest = mockk<Request>()

    private val frontCommandFunction = FrontCommandFunction { spiedFrontCommand }

    @Test
    fun whenInvokeItShouldCallInitWithRequest() {
        every { spiedFrontCommand.response } returns Response("Test Response", HttpStatus.OK)

        val expectedResponse = Response("Test Response", HttpStatus.OK)
        assertEquals(expectedResponse, frontCommandFunction(mockRequest))
    }

    @Test
    fun whenInvokeItShouldEnsureOrderedFrontCommandExecution() {
        every { spiedFrontCommand.response } returns Response("Test Response", HttpStatus.OK)

        frontCommandFunction(mockRequest)

        verifyOrder {
            spiedFrontCommand.init(mockRequest, any())
            spiedFrontCommand.process()
            spiedFrontCommand.response
        }
    }
}