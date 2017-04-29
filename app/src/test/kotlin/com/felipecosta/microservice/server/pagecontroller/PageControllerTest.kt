package com.felipecosta.microservice.server.pagecontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.renderer.Renderer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class PageControllerTest {

    val renderer: Renderer = mock()

    lateinit var pageController: PageController

    @Before
    fun setUp() {

        pageController = spy(object : PageController(renderer) {
            override fun doGet(request: Request) {

            }
        })
    }

    @Test
    fun whenRenderWithTemplateThenVerifyOutput() {
        whenever(renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html")).thenReturn("Awesome return")

        val expectedOutput = "Awesome return"
        pageController.render(template = "views/hello_world_test.html")
        val actualOutput = pageController.output
        Assert.assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun whenRenderWithTextThenVerifyOutput() {
        val expectedOutput = "{\"value:\" \"Hello\"}"
        pageController.render(text = "{\"value:\" \"Hello\"}")
        val actualOutput = pageController.output
        Assert.assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun givenMockRendererWhenRenderWithTextThenVerifyOutput() {
        pageController.render(text = "{\"value:\" \"Hello\"}")
        Mockito.verifyZeroInteractions(renderer)
    }

}