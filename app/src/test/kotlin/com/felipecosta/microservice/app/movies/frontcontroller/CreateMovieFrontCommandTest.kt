package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.NotImplementedRequest
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.HttpStatus
import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import kotlin.test.assertEquals

class CreateMovieFrontCommandTest {

    val mockMoviesRepository: MoviesRepository = mock()

    val frontCommand = CreateMovieFrontCommand().apply {
        moviesRepository = mockMoviesRepository
    }

    @Test
    fun givenJsonBodyThenAssertResponseWithCreatedMovie() {
        frontCommand.init(object : Request by NotImplementedRequest() {
            override val body: String = """{"name":"New movie"}"""
        })

        whenever(mockMoviesRepository.save(eq(Movie("New movie")))).thenReturn(Movie("New movie", 1))

        frontCommand.process()

        assertEquals(Response("""{"response":{"name":"New movie","id":1}}""", HttpStatus.CREATED), frontCommand.response)
    }


}