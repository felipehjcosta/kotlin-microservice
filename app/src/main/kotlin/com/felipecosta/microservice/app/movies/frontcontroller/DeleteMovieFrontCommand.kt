package com.felipecosta.microservice.app.movies.frontcontroller

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import javax.inject.Inject

class DeleteMovieFrontCommand : FrontCommand() {

    @Inject
    lateinit var moviesRepository: MoviesRepository

    override fun process() {
        val movie = moviesRepository.find(request.routeParams[":id"]?.toInt() ?: -1)

        moviesRepository.delete(movie!!)
    }
}