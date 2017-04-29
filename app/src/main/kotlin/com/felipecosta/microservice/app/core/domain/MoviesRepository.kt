package com.felipecosta.microservice.app.core.domain

import com.felipecosta.microservice.app.core.domain.entity.Movie

interface MoviesRepository {
    fun findAll(): List<Movie>
}