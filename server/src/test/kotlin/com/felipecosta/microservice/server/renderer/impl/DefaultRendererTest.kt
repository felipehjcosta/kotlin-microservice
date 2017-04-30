package com.felipecosta.microservice.server.renderer.impl

class DefaultRendererTest {

    val obj: Any = com.nhaarman.mockito_kotlin.mock()

    lateinit var defaultRenderer: DefaultRenderer

    @org.junit.Before
    fun setUp() {
        defaultRenderer = DefaultRenderer()
    }

    @org.junit.Test
    fun givenObjectWhenRenderThenVerifyOutputText() {
        val expectedOutput = "test output"
        val actualOutput = defaultRenderer.render(obj, "views/test.html")
        kotlin.test.assertEquals(expectedOutput, actualOutput)
    }
}