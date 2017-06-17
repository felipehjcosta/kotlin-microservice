package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.Response
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import kotlin.test.assertEquals

class ListMoviesFrontCommandTest {

    lateinit var listMoviesFrontCommand: ListMoviesFrontCommand

    val moviesRepository: MoviesRepository = mock()

    @Before
    fun setUp() {
        listMoviesFrontCommand = ListMoviesFrontCommand()
        listMoviesFrontCommand.moviesRepository = moviesRepository
    }

    @Test
    fun givenEmptyMovieListWhenProcessThenAssertResponseWithEmptyJsonList() {
        given(moviesRepository.findAll()).willReturn(emptyList())

        listMoviesFrontCommand.process()

        assertEquals(Response("""{"response":[]}""", 200), listMoviesFrontCommand.response)
    }

    @Test
    fun givenSingleMovieListWhenProcessThenAssertResponseWithSingleMovieJsonList() {
        given(moviesRepository.findAll()).willReturn(listOf(Movie("Awesome movie", 1)))

        listMoviesFrontCommand.process()

        assertEquals(Response("""{"response":[{"name":"Awesome movie","id":1}]}""", 200), listMoviesFrontCommand.response)
    }
}