package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import kotlin.test.assertEquals

class MoviesFrontCommandTest {

    lateinit var moviesFrontCommand: MoviesFrontCommand

    val moviesRepository: MoviesRepository = mock()

    @Before
    fun setUp() {
        moviesFrontCommand = MoviesFrontCommand()
        moviesFrontCommand.moviesRepository = moviesRepository
    }

    @Test
    fun givenEmptyMovieListWhenProcessThenRenderEmptyJsonList() {
        given(moviesRepository.findAll()).willReturn(emptyList())

        moviesFrontCommand.process()

        assertEquals("""{"response":[]}""", moviesFrontCommand.output)
    }

    @Test
    fun givenSingleMovieListWhenProcessThenRenderSingleMovieJsonList() {
        given(moviesRepository.findAll()).willReturn(listOf(Movie("Awesome movie", 0)))

        moviesFrontCommand.process()

        assertEquals("""{"response":[{"name":"Awesome movie"}]}""", moviesFrontCommand.output)
    }
}