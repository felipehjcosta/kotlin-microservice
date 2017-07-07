package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(SpringBootServerRestController::class)
class SpringBootServerRestControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @After
    fun tearDown() {
        ServerUrlMappings.clear()
    }

    @Test
    fun givenNotRegisteredPathWhenHandlerThenItReturnsNotFound() {
        mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isNotFound)
    }

    @Test
    fun givenRegisteredGetPathWhenHandlerThenItHandlesPath() {
        val stubFrontCommand = object : FrontCommand() {
            override fun process() {
                render(text = "Hello World")
            }
        }

        ServerUrlMappings[GetPath("/")] = {
            stubFrontCommand.process()
            stubFrontCommand.response
        }

        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andExpect(content().string("Hello World"))
    }
}