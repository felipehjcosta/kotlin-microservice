package com.felipecosta.microservice.app.movies.frontcontroller

import com.beust.klaxon.json
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class GetMovieFrontCommand : FrontCommand() {

    lateinit var moviesRepository: MoviesRepository

    override fun process() {
        val movie = moviesRepository.find(request.routeParams[":id"]?.toInt() ?: -1)

        val jsonBody = json {
            obj("response" to if (movie != null) {
                with(movie) {
                    obj("name" to name, "id" to id)
                }
            } else {
                null
            })
        }

        render(text = jsonBody.toJsonString())
    }
}