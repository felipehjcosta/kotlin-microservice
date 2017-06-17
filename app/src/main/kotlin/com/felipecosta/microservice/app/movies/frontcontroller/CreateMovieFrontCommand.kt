package com.felipecosta.microservice.app.movies.frontcontroller

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.json
import com.beust.klaxon.string
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import com.felipecosta.microservice.server.HttpStatus
import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import javax.inject.Inject

class CreateMovieFrontCommand : FrontCommand() {

    @Inject
    lateinit var moviesRepository: MoviesRepository

    override fun process() {
        val jsonObject = Parser().parse(request.body.byteInputStream()) as JsonObject
        val movie = Movie(jsonObject.string("name")!!)

        val savedMovie = moviesRepository.save(movie)

        val jsonBody = json {
            obj("response" to with(savedMovie) {
                obj("name" to name, "id" to id)
            })
        }

        render(text = jsonBody.toJsonString(), code = HttpStatus.CREATED)

    }
}