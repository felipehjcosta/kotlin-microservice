package com.felipecosta.microservice.renderer.impl

import com.felipecosta.microservice.utils.mock
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DefaultRendererTest {

    val obj: Any = mock()

    var defaultRenderer: DefaultRenderer? = null

    @Before
    fun setUp() {
        defaultRenderer = DefaultRenderer()
    }

    @Test
    fun name() {
        val expectedOutput = "test output"
        val actualOutput = defaultRenderer!!.render(obj, "views/test.html")
        assertEquals(expectedOutput, actualOutput)
    }
}