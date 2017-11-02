package com.felipecosta.microservice.app.core.data

import com.felipecosta.microservice.app.core.domain.entity.Movie
import io.lettuce.core.RedisClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.testcontainers.containers.GenericContainer
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue


class RedisMoviesRepositoryTest {

    @get:Rule
    val redisRule: GenericContainer<Nothing> = GenericContainer<Nothing>("redis:3.2.9").apply {
        addExposedPort(6379)
    }

    private lateinit var redisUri: String

    private lateinit var repository: RedisMoviesRepository

    @Before
    fun setUp() {
        redisUri = "${redisRule.containerIpAddress}:${redisRule.getMappedPort(6379)}"
        repository = RedisMoviesRepository(redisUri)
    }

    @Test
    fun givenEmptyDataWhenFindAllThenItIsEmpty() {
        assertTrue { repository.findAll().isEmpty() }
    }

    @Test
    fun givenDataWhenFindAllThenItIsNotEmpty() {
        createDummy()

        assertFalse { repository.findAll().isEmpty() }
    }

    @Test
    fun givenEmptyDataWhenFindThenReturnMovie() {
        assertNull(repository.find(0))
    }

    @Test
    fun givenDataWhenFindThenReturnMovie() {
        createDummy()

        assertEquals(Movie("Awesome Marvel Movie", 0), repository.find(0))
    }

    @Test
    fun givenEmptyDataWhenSaveThenAssertSaved() {
        val movie = Movie("New Movie")
        val savedMovie = repository.save(movie)
        val expectedMovie = Movie("New Movie", 0)
        assertEquals(expectedMovie, savedMovie)
    }

    @Test
    fun givenDataWhenSaveThenAssertSaved() {
        createDummy()

        val movie = Movie("New Movie")
        val savedMovie = repository.save(movie)
        val expectedMovie = Movie("New Movie", 1)
        assertEquals(expectedMovie, savedMovie)
    }

    @Test
    fun givenEmptyDataWhenDeleteThenShouldDoNothing() {
        repository.delete(Movie("Dummy", 0))
    }

    @Test
    fun givenDataWhenDeleteThenAssertDeleted() {
        createDummy()

        repository.delete(repository.find(0)!!)
        assertNull(repository.find(0))
    }

    @Test
    fun givenEmptyDataWhenUpdateThenShouldDoNothing() {
        repository.update(Movie("Awesome Marvel Movie", 1))

        assertNull(repository.find(1))
    }

    @Test
    fun givenDataWhenUpdateThenAssertUpdated() {
        createDummy()

        val movie = repository.find(0)!!
        val editedMovie = movie.copy(name = "Edit Movie")
        repository.update(editedMovie)
        assertEquals(editedMovie, repository.find(0))
    }

    private fun createDummy() {
        val client = RedisClient.create("redis://$redisUri")
        val connection = client.connect()

        val commands = connection.sync()

        commands.hmset("movie:0", mapOf("name" to "Awesome Marvel Movie", "id" to "0"))

        connection.close()
        client.shutdown()
    }
}