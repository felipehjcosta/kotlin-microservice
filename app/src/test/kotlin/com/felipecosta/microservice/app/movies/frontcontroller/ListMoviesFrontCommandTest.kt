package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.Response
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class ListMoviesFrontCommandTest {

    private val mockMoviesRepository = mockk<MoviesRepository>()

    private var listMoviesFrontCommand = ListMoviesFrontCommand(mockMoviesRepository)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun givenEmptyMovieListWhenProcessThenAssertResponseWithEmptyJsonList() {
        every { mockMoviesRepository.findAll() } returns emptyList()

        listMoviesFrontCommand.process()

        assertEquals(Response("""{"response":[]}""", 200), listMoviesFrontCommand.response)
    }

    @Test
    fun givenSingleMovieListWhenProcessThenAssertResponseWithSingleMovieJsonList() {
        every { mockMoviesRepository.findAll() } returns listOf(Movie("Awesome movie", 1))

        listMoviesFrontCommand.process()

        assertEquals(Response("""{"response":[{"name":"Awesome movie","id":1}]}""", 200), listMoviesFrontCommand.response)
    }
}