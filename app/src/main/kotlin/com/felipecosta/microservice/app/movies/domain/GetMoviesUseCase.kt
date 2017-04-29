package com.felipecosta.microservice.app.movies.domain

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.UseCase
import com.felipecosta.microservice.app.core.domain.entity.Movie

class GetMoviesUseCase(private val moviesRepository: MoviesRepository) : UseCase<Any, List<Movie>> {
    override fun execute(argument: Any?): List<Movie> = moviesRepository.findAll()
}