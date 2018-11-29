package com.felipecosta.microservice.app.notes.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import com.felipecosta.microservice.server.renderer.Renderer
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class NotesFrontCommandTest {

    private val renderer = mockk<Renderer>()

    private val request = mockk<Request>()

    private val notesFrontCommand = NotesFrontCommand()

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun givenOutputObjectWhenProcessThenVerifyResponse() {
        val expectedBody = "Awesome output"
        every {
            renderer.render(Output("My First Website", "My Interesting Content"), "views/notes.html")
        } answers {
            expectedBody
        }

        notesFrontCommand.apply {
            init(request, renderer)
            notesFrontCommand.process()
        }

        assertEquals(Response(expectedBody, 200), notesFrontCommand.response)
    }
}