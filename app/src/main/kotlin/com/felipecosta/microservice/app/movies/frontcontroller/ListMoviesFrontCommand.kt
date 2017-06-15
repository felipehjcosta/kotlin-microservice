package com.felipecosta.microservice.app.movies.frontcontroller

import com.beust.klaxon.json
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import javax.inject.Inject

class ListMoviesFrontCommand : FrontCommand() {

    @Inject
    lateinit var moviesRepository: MoviesRepository

    override fun process() {
        val movies = moviesRepository.findAll()

        val jsonBody = json {
            obj("response" to array(movies.map {
                obj("name" to it.name, "id" to it.id)
            }))
        }

        render(text = jsonBody.toJsonString())
    }
}