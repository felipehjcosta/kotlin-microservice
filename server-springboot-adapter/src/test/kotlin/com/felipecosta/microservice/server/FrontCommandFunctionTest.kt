package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import com.nhaarman.mockito_kotlin.*
import org.junit.Test
import kotlin.test.assertEquals

class FrontCommandFunctionTest {

    val spiedFrontCommand = spy(object : FrontCommand() {
        override fun process() {

        }
    })

    val mockRequest = mock<Request>()

    val frontCommandFunction = FrontCommandFunction { spiedFrontCommand }

    @Test
    fun whenInvokeItShouldCallInitWithRequest() {
        whenever(spiedFrontCommand.response).thenReturn(Response("Test Response", HttpStatus.OK))

        val expectedResponse = Response("Test Response", HttpStatus.OK)
        assertEquals(expectedResponse, frontCommandFunction(mockRequest))
    }

    @Test
    fun whenInvokeItShouldEnsureOrderedFrontCommandExecution() {
        whenever(spiedFrontCommand.response).thenReturn(Response("Test Response", HttpStatus.OK))

        frontCommandFunction(mockRequest)

        val inOrder = inOrder(spiedFrontCommand)
        inOrder.verify(spiedFrontCommand).init(eq(mockRequest), any())
        inOrder.verify(spiedFrontCommand).process()
        inOrder.verify(spiedFrontCommand).response
    }
}