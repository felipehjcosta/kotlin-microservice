package com.felipecosta.microservice.server.pagecontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.renderer.Renderer
import io.mockk.*

class PageControllerTest {

    private val renderer = mockk<Renderer>()

    private var pageController = spyk(object : PageController(renderer) {
        override fun doGet(request: Request) {}
    })

    @org.junit.Test
    fun whenRenderWithTemplateThenVerifyOutput() {
        every { renderer.render(emptyMap<String, Any>(), "views/hello_world_test.html") } returns "Awesome return"

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
        verify { renderer wasNot Called }
    }

}