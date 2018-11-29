package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class FrontCommandFunctionTest {

    private val spiedFrontCommand = spyk<FrontCommand>()

    private val mockRequest = mockk<Request>()

    private val frontCommandFunction = FrontCommandFunction { spiedFrontCommand }

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

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