package com.felipecosta.microservice.app.core.data

import com.felipecosta.microservice.app.core.domain.entity.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StubMovieRepositoryTest {

    lateinit var stubMovieRepository: StubMovieRepository

    @Before
    fun setUp() {
        stubMovieRepository = StubMovieRepository()
    }

    @Test
    fun whenFindAllThenReturnSingleMovieList() {
        assertEquals(listOf(Movie("Awesome Marvel Movie")), stubMovieRepository.findAll())
    }
}
