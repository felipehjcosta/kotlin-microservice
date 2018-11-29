package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Duration

@ExtendWith(SpringExtension::class)
@WebMvcTest(ServerRestController::class)
@ContextConfiguration(classes = [ServerRestController::class])
class ServerRestControllerTest {

    companion object {
        private const val TIMEOUT = 5000L
    }

    @Autowired
    lateinit var mvc: MockMvc

    @AfterEach
    fun tearDown() {
        ServerUrlMappings.clear()
    }

    @Test
    fun givenNotRegisteredGetPathWhenPerformGetThenAssertNotFound() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
            mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isNotFound)
        }
    }

    @Test
    fun givenRegisteredGetPathWhenPerformGetThenAssertGetResponse() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
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

    @Test
    fun givenRegisteredGetPathWithVariableWhenPerformGetThenAssertGetResponse() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
            val stubFrontCommand = object : FrontCommand() {
                override fun process() {
                    render(text = "testing GET")
                }
            }

            ServerUrlMappings[GetPath("/:id")] = {
                stubFrontCommand.process()
                stubFrontCommand.response
            }

            mvc.perform(MockMvcRequestBuilders.get("/42"))
                    .andExpect(status().isOk)
                    .andExpect(content().string("testing GET"))
        }
    }

    @Test
    fun givenRegisteredPostPathWhenPerformPostThenAssertPostResponse() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
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
    }

    @Test
    fun givenRegisteredPostPathWithVariableWhenPerformPostThenAssertPostResponse() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
            val stubFrontCommand = object : FrontCommand() {
                override fun process() {
                    render(text = "post response")
                }
            }

            ServerUrlMappings[PostPath("/:id")] = {
                stubFrontCommand.process()
                stubFrontCommand.response
            }

            mvc.perform(MockMvcRequestBuilders.post("/42"))
                    .andExpect(status().isOk)
                    .andExpect(content().string("post response"))
        }
    }

    @Test
    fun givenRegisteredPutPathWhenPerformPutThenAssertPutResponse() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
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
    }

    @Test
    fun givenRegisteredPutPathWithVariableWhenPerformPutThenAssertPutResponse() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
            val stubFrontCommand = object : FrontCommand() {
                override fun process() {
                    render(text = "put response")
                }
            }

            ServerUrlMappings[PutPath("/:id")] = {
                stubFrontCommand.process()
                stubFrontCommand.response
            }

            mvc.perform(MockMvcRequestBuilders.put("/42"))
                    .andExpect(status().isOk)
                    .andExpect(content().string("put response"))
        }
    }

    @Test
    fun givenRegisteredDeletePathWhenPerformDeleteThenAssertDeleteResponse() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
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

    @Test
    fun givenRegisteredDeletePatWithVariableWhenPerformDeleteThenAssertDeleteResponse() {
        assertTimeout(Duration.ofMillis(TIMEOUT)) {
            val stubFrontCommand = object : FrontCommand() {
                override fun process() {
                    render(text = "delete response")
                }
            }

            ServerUrlMappings[DeletePath("/:id")] = {
                stubFrontCommand.process()
                stubFrontCommand.response
            }

            mvc.perform(MockMvcRequestBuilders.delete("/42"))
                    .andExpect(status().isOk)
                    .andExpect(content().string("delete response"))
        }
    }
}