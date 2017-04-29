package com.felipecosta.microservice.app.core.data

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie

class StubMovieRepository : MoviesRepository {
    override fun findAll(): List<Movie> = listOf(Movie("Awesome Marvel Movie"))
}