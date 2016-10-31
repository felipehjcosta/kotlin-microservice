package com.felipecosta.microservice.server.renderer.impl

import com.felipecosta.microservice.utils.mock
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DefaultRendererTest {

    val obj: Any = mock()

    lateinit var defaultRenderer: DefaultRenderer

    @Before
    fun setUp() {
        defaultRenderer = DefaultRenderer()
    }

    @Test
    fun givenObjectWhenRenderThenVerifyOutputText() {
        val expectedOutput = "test output"
        val actualOutput = defaultRenderer.render(obj, "views/test.html")
        assertEquals(expectedOutput, actualOutput)
    }
}