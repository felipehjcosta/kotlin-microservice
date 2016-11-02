package com.felipecosta.microservice.app.movies.frontcontroller

import com.beust.klaxon.json
import com.felipecosta.microservice.app.movies.domain.GetMoviesUseCase
import com.felipecosta.microservice.server.frontcontroller.SparkFrontCommand
import javax.inject.Inject

class MoviesFrontCommand() : SparkFrontCommand() {

    @Inject
    lateinit var getMoviesUseCase: GetMoviesUseCase

    override fun process() {
        val movies = getMoviesUseCase.execute()

        val jsonBody = json {
            array(movies.map { obj("name" to it.name) })
        }

        render(text = jsonBody.toJsonString())
    }
}