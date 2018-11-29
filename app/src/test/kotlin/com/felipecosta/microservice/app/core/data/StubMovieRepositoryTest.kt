package com.felipecosta.microservice.app.core.data

import com.felipecosta.microservice.app.core.domain.entity.Movie
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull

class StubMovieRepositoryTest {

    private val stubMovieRepository = StubMovieRepository()

    @Test
    fun whenFindAllThenItIsNotEmpty() {
        assertFalse { stubMovieRepository.findAll().isEmpty() }
    }

    @Test
    fun givenIdWhenFindThenReturnMovie() {
        assertEquals(Movie("Awesome Marvel Movie", 0), stubMovieRepository.find(0))
    }

    @Test
    fun whenSaveThenAssertSaved() {
        val movie = Movie("New Movie")
        val savedMovie = stubMovieRepository.save(movie)
        val expectedMovie = Movie("New Movie", 4)
        assertEquals(expectedMovie, savedMovie)
    }

    @Test
    fun whenDeleteThenAssertDeleted() {
        stubMovieRepository.delete(stubMovieRepository.find(3)!!)
        assertNull(stubMovieRepository.find(3))
    }

    @Test
    fun whenUpdateThenAssertUpdated() {
        val movie = stubMovieRepository.find(3)!!
        val editedMovie = movie.copy(name = "Edit Movie")
        stubMovieRepository.update(editedMovie)
        assertEquals(editedMovie, stubMovieRepository.find(3))
    }
}
