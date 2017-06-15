package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.Request
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import kotlin.test.assertEquals

class GetMovieFrontCommandTest {

    val mockMoviesRepository: MoviesRepository = mock()

    val frontCommand = GetMovieFrontCommand().apply {
        moviesRepository = mockMoviesRepository
    }

    @Test
    fun givenIdWhenProcessThenRenderSingleMovie() {
        frontCommand.init(object : Request {
            override val queryParams: Map<String, Array<String>>
                get() = TODO("not implemented")
            override val routeParams: Map<String, String>
                get() = mapOf(":id" to "1")
            override val body: String
                get() = TODO("not implemented")
            override val url: String
                get() = TODO("not implemented")
            override val host: String
                get() = TODO("not implemented")
            override val userAgent: String
                get() = TODO("not implemented")
        })

        whenever(mockMoviesRepository.find(1)).thenReturn(Movie("Awesome movie", 1))

        frontCommand.process()

        assertEquals("""{"response":{"name":"Awesome movie"}}""", frontCommand.output)
    }

    @Test
    fun givenInvalidIdWhenProcessThenRenderEmpty() {
        frontCommand.init(object : Request {
            override val queryParams: Map<String, Array<String>>
                get() = TODO("not implemented")
            override val routeParams: Map<String, String>
                get() = mapOf(":id" to "1")
            override val body: String
                get() = TODO("not implemented")
            override val url: String
                get() = TODO("not implemented")
            override val host: String
                get() = TODO("not implemented")
            override val userAgent: String
                get() = TODO("not implemented")
        })

        whenever(mockMoviesRepository.find(1)).thenReturn(null)

        frontCommand.process()

        assertEquals("""{"response":null}""", frontCommand.output)
    }
}