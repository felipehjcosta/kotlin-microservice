package com.felipecosta.microservice.frontcontroller.impl

import com.felipecosta.microservice.renderer.Renderer
import com.felipecosta.microservice.utils.mock
import com.felipecosta.microservice.utils.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import spark.Request
import spark.Response

class NotesFrontCommandTest {

    val renderer: Renderer = mock()

    val request: Request = mock()

    val response: Response = mock()

    var notesFrontCommand: NotesFrontCommand? = null

    @Before
    fun setUp() {
        notesFrontCommand = NotesFrontCommand(request, response)
    }

    @Test
    fun name() {
        val expectedOutput = "Awesome output"
        whenever(renderer.render(NotesFrontCommand.Output("My First Website", "My Interesting Content"), "views/notes.html")).
                thenReturn(expectedOutput)

        notesFrontCommand?.init(renderer)

        notesFrontCommand?.process()

        assertEquals(expectedOutput, notesFrontCommand?.output)
    }
}