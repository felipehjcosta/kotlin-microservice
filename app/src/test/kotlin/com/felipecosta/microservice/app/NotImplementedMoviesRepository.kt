package com.felipecosta.microservice.app

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie

class NotImplementedMoviesRepository : MoviesRepository {

    override fun findAll(): List<Movie> {
        TODO("not implemented")
    }

    override fun find(id: Int): Movie? {
        TODO("not implemented")
    }

    override fun save(movie: Movie): Movie {
        TODO("not implemented")
    }

    override fun update(movie: Movie) {
        TODO("not implemented")
    }

    override fun delete(movie: Movie) {
        TODO("not implemented")
    }
}