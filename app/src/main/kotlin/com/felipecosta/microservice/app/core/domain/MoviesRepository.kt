package com.felipecosta.microservice.app.core.domain

import com.felipecosta.microservice.app.core.domain.entity.Movie

interface MoviesRepository {
    fun findAll(): List<Movie>

    fun find(id: Int = -1): Movie?

    fun save(movie: Movie): Movie

    fun update(movie: Movie)

    fun delete(movie: Movie)
}