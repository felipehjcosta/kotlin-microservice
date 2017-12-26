package com.felipecosta.microservice.app.notes.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import com.felipecosta.microservice.server.renderer.Renderer
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class NotesFrontCommandTest {

    private val renderer = mockk<Renderer>()

    private val request = mockk<Request>()

    private val notesFrontCommand = NotesFrontCommand()

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