package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
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
    fun givenEmptyMovieListWhenProcessThenRenderEmptyJsonList() {
        given(moviesRepository.findAll()).willReturn(emptyList())

        listMoviesFrontCommand.process()

        assertEquals("""{"response":[]}""", listMoviesFrontCommand.output)
    }

    @Test
    fun givenSingleMovieListWhenProcessThenRenderSingleMovieJsonList() {
        given(moviesRepository.findAll()).willReturn(listOf(Movie("Awesome movie", 0)))

        listMoviesFrontCommand.process()

        assertEquals("""{"response":[{"name":"Awesome movie"}]}""", listMoviesFrontCommand.output)
    }
}