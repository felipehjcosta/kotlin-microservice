package com.felipecosta.microservice.app.movies.frontcontroller

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.json
import com.beust.klaxon.string
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class UpdateMovieFrontCommand : FrontCommand() {

    lateinit var moviesRepository: MoviesRepository

    override fun process() {
        val oldMovie = moviesRepository.find(request.routeParams[":id"]?.toInt() ?: -1)

        val jsonObject = Parser().parse(request.body.byteInputStream()) as JsonObject
        val newMovie = oldMovie!!.copy(name = jsonObject.string("name")!!)

        moviesRepository.update(newMovie)

        val jsonBody = json {
            obj("response" to with(newMovie) {
                obj("name" to name, "id" to id)
            })
        }

        render(text = jsonBody.toJsonString())
    }
}