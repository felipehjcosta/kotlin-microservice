package com.felipecosta.microservice.app.notes.frontcontroller

import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import com.felipecosta.microservice.server.renderer.Renderer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotesFrontCommandTest {

    val renderer: Renderer = mock()

    val request: Request = mock()

    lateinit var notesFrontCommand: NotesFrontCommand

    @Before
    fun setUp() {
        notesFrontCommand = NotesFrontCommand()
    }

    @Test
    fun givenOutputObjectWhenProcessThenVerifyResponse() {
        val expectedBody = "Awesome output"
        whenever(renderer.render(Output("My First Website", "My Interesting Content"), "views/notes.html")).
                thenReturn(expectedBody)

        notesFrontCommand.apply {
            init(request, renderer)
            notesFrontCommand.process()
        }

        assertEquals(Response(expectedBody, 200), notesFrontCommand.response)
    }
}