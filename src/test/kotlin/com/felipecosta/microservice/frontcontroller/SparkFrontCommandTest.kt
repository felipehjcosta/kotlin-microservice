package com.felipecosta.microservice.frontcontroller

import com.felipecosta.microservice.renderer.Renderer
import com.felipecosta.microservice.utils.mock
import com.felipecosta.microservice.utils.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Matchers.eq
import org.mockito.Mockito

class SparkFrontCommandTest {

    val renderer: Renderer = mock()

    val sparkFrontCommand: SparkFrontCommand = mock(Mockito.CALLS_REAL_METHODS)

    @Before
    fun setUp() {
    }

    @Test
    fun name() {
        sparkFrontCommand.init()

        val expectedOutput = "Hello World form Spark Front Command"
        sparkFrontCommand.render(template = "views/hello_world_test.html")
        val actualOutput = sparkFrontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun name2() {
        sparkFrontCommand.init(renderer)
        whenever(renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html")).thenReturn("Awesome return")

        val expectedOutput = "Awesome return"
        sparkFrontCommand.render(template = "views/hello_world_test.html")
        val actualOutput = sparkFrontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun name3() {
        val expectedOutput = ""
        sparkFrontCommand.render(template = "views/hello_world_test.html")
        val actualOutput = sparkFrontCommand.output
        assertEquals(expectedOutput, actualOutput)
    }
}