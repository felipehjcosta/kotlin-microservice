package com.felipecosta.microservice.server.pagecontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.renderer.Renderer
import com.nhaarman.mockito_kotlin.mock

class PageControllerTest {

    val renderer: Renderer = mock()

    lateinit var pageController: PageController

    @org.junit.Before
    fun setUp() {

        pageController = com.nhaarman.mockito_kotlin.spy(object : PageController(renderer) {
            override fun doGet(request: Request) {

            }
        })
    }

    @org.junit.Test
    fun whenRenderWithTemplateThenVerifyOutput() {
        com.nhaarman.mockito_kotlin.whenever(renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html")).thenReturn("Awesome return")

        val expectedOutput = "Awesome return"
        pageController.render(template = "views/hello_world_test.html")
        val actualOutput = pageController.output
        org.junit.Assert.assertEquals(expectedOutput, actualOutput)
    }

    @org.junit.Test
    fun whenRenderWithTextThenVerifyOutput() {
        val expectedOutput = "{\"value:\" \"Hello\"}"
        pageController.render(text = "{\"value:\" \"Hello\"}")
        val actualOutput = pageController.output
        org.junit.Assert.assertEquals(expectedOutput, actualOutput)
    }

    @org.junit.Test
    fun givenMockRendererWhenRenderWithTextThenVerifyOutput() {
        pageController.render(text = "{\"value:\" \"Hello\"}")
        org.mockito.Mockito.verifyZeroInteractions(renderer)
    }

}