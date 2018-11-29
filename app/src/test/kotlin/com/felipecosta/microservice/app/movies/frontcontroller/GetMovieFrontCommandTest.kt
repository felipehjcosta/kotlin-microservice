package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.NotImplementedRequest
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class GetMovieFrontCommandTest {

    private val mockMoviesRepository = mockk<MoviesRepository>()

    private val frontCommand = GetMovieFrontCommand(mockMoviesRepository)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun givenIdWhenProcessThenAssertResponseWithSingleMovie() {
        frontCommand.init(object : Request by NotImplementedRequest() {
            override val routeParams: Map<String, String>
                get() = mapOf(":id" to "1")
        })

        every { mockMoviesRepository.find(1) } returns Movie("Awesome movie", 1)

        frontCommand.process()

        assertEquals(Response("""{"response":{"name":"Awesome movie","id":1}}""", 200), frontCommand.response)
    }

    @Test
    fun givenInvalidIdWhenProcessThenAssertResponseWithNull() {
        frontCommand.init(object : Request by NotImplementedRequest() {
            override val routeParams: Map<String, String>
                get() = mapOf(":id" to "1")
        })

        every { mockMoviesRepository.find(1) } returns null

        frontCommand.process()

        assertEquals(Response("""{"response":null}""", 200), frontCommand.response)
    }
}