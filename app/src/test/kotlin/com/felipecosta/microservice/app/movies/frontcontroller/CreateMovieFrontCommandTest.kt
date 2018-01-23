package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.NotImplementedRequest
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.HttpStatus
import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class CreateMovieFrontCommandTest {

    private val mockMoviesRepository = mockk<MoviesRepository>()

    private val frontCommand = CreateMovieFrontCommand(mockMoviesRepository)

    @Test
    fun givenJsonBodyThenAssertResponseWithCreatedMovie() {
        frontCommand.init(object : Request by NotImplementedRequest() {
            override val body: String = """{"name":"New movie"}"""
        })

        every { mockMoviesRepository.save(Movie("New movie")) } returns Movie("New movie", 1)

        frontCommand.process()

        assertEquals(Response("""{"response":{"name":"New movie","id":1}}""", HttpStatus.CREATED), frontCommand.response)
    }
}