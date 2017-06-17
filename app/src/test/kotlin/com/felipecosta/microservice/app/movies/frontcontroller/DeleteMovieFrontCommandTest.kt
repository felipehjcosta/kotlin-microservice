package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.NotImplementedRequest
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import kotlin.test.assertEquals

class DeleteMovieFrontCommandTest {
    val mockMoviesRepository = mock<MoviesRepository>()

    val frontCommand = DeleteMovieFrontCommand().apply {
        moviesRepository = mockMoviesRepository
    }

    @Test
    fun givenMovieIdThenAssertResponseOk() {
        frontCommand.init(object : Request by NotImplementedRequest() {
            override val routeParams = mapOf(":id" to "1")
        })

        val movie = Movie("Any movie", 1)

        whenever(mockMoviesRepository.find(eq(1))).thenReturn(movie)

        frontCommand.process()

        assertEquals(Response("", 200), frontCommand.response)
    }

    @Test
    fun givenMovieIdThenVerifyDeletedOnRepository() {
        frontCommand.init(object : Request by NotImplementedRequest() {
            override val routeParams = mapOf(":id" to "1")
        })

        val movie = Movie("Any movie", 1)

        whenever(mockMoviesRepository.find(eq(1))).thenReturn(movie)

        frontCommand.process()

        verify(mockMoviesRepository).delete(eq(movie))
    }
}