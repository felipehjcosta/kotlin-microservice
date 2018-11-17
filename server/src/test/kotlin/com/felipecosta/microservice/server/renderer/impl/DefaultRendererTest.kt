package com.felipecosta.microservice.server.renderer.impl

import io.mockk.mockk

class DefaultRendererTest {

    private val obj = mockk<Any>()

    private val defaultRenderer = DefaultRenderer()

    @org.junit.Test
    fun givenObjectWhenRenderThenVerifyOutputText() {
        val expectedOutput = "test output"
        val actualOutput = defaultRenderer.render(obj, "views/test.html")
        kotlin.test.assertEquals(expectedOutput, actualOutput)
    }
}