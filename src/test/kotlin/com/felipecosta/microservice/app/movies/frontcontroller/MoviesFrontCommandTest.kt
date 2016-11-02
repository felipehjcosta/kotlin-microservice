package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.app.movies.domain.GetMoviesUseCase
import com.felipecosta.microservice.utils.mock
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import kotlin.test.assertEquals

class MoviesFrontCommandTest {

    lateinit var moviesFrontCommand: MoviesFrontCommand

    val getMoviesUseCase: GetMoviesUseCase = mock()

    @Before
    fun setUp() {
        moviesFrontCommand = MoviesFrontCommand()
        moviesFrontCommand.getMoviesUseCase = getMoviesUseCase
    }

    @Test
    fun givenEmptyMovieListWhenProcessThenRenderEmptyJsonList() {
        given(getMoviesUseCase.execute()).willReturn(emptyList())

        moviesFrontCommand.process()

        assertEquals("[]", moviesFrontCommand.output)
    }

    @Test
    fun givenSingleMovieListWhenProcessThenRenderSingleMovieJsonList() {
        given(getMoviesUseCase.execute()).willReturn(listOf(Movie("Awesome movie")))

        moviesFrontCommand.process()

        assertEquals("[{\"name\":\"Awesome movie\"}]", moviesFrontCommand.output)
    }
}