package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.NotImplementedRequest
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.Request
import com.felipecosta.microservice.server.Response
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class UpdateMovieFrontCommandTest {
    private val mockMoviesRepository = mockk<MoviesRepository>()

    private val frontCommand = UpdateMovieFrontCommand(mockMoviesRepository)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun givenJsonBodyThenAssertResponseWithUpdatedMovie() {
        frontCommand.init(object : Request by NotImplementedRequest() {
            override val body: String = """{"name":"New movie"}"""

            override val routeParams = mapOf(":id" to "1")
        })

        every { mockMoviesRepository.find(1) } returns Movie("Old movie", 1)
        every { mockMoviesRepository.update(Movie("New movie", 1)) } just Runs

        frontCommand.process()

        assertEquals(Response("""{"response":{"name":"New movie","id":1}}""", 200), frontCommand.response)
    }
}