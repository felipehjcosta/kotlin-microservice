package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(SpringBootServerRestController::class)
@ContextConfiguration(classes = arrayOf(SpringBootServerRestController::class))
class SpringBootServerRestControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @After
    fun tearDown() {
        ServerUrlMappings.clear()
    }

    @Test(timeout = 5000L)
    fun givenNotRegisteredGetPathWhenPerformGetThenAssertNotFound() {
        mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isNotFound)
    }

    @Test(timeout = 5000L)
    fun givenRegisteredGetPathWhenPerformGetThenAssertGetResponse() {
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

    @Test(timeout = 5000L)
    fun givenRegisteredPostPathWhenPerformPostThenAssertPostResponse() {
        val stubFrontCommand = object : FrontCommand() {
            override fun process() {
                render(text = "post response")
            }
        }

        ServerUrlMappings[PostPath("/")] = {
            stubFrontCommand.process()
            stubFrontCommand.response
        }

        mvc.perform(MockMvcRequestBuilders.post("/"))
                .andExpect(status().isOk)
                .andExpect(content().string("post response"))
    }

    @Test(timeout = 5000L)
    fun givenRegisteredPutPathWhenPerformPutThenAssertPutResponse() {
        val stubFrontCommand = object : FrontCommand() {
            override fun process() {
                render(text = "put response")
            }
        }

        ServerUrlMappings[PutPath("/")] = {
            stubFrontCommand.process()
            stubFrontCommand.response
        }

        mvc.perform(MockMvcRequestBuilders.put("/"))
                .andExpect(status().isOk)
                .andExpect(content().string("put response"))
    }

    @Test(timeout = 5000L)
    fun givenRegisteredDeletePathWhenPerformDeleteThenAssertDeleteResponse() {
        val stubFrontCommand = object : FrontCommand() {
            override fun process() {
                render(text = "delete response")
            }
        }

        ServerUrlMappings[DeletePath("/")] = {
            stubFrontCommand.process()
            stubFrontCommand.response
        }

        mvc.perform(MockMvcRequestBuilders.delete("/"))
                .andExpect(status().isOk)
                .andExpect(content().string("delete response"))
    }
}