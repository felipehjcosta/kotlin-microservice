package com.felipecosta.microservice

import com.felipecosta.microservice.app.core.di.DaggerApplicationComponent
import com.felipecosta.microservice.app.movies.di.DaggerMoviesComponent
import com.felipecosta.microservice.app.movies.frontcontroller.MoviesFrontCommand
import com.felipecosta.microservice.server.*
import com.felipecosta.microservice.server.renderer.impl.PebbleRenderer
import com.mitchellbosecke.pebble.PebbleEngine

fun main(args: Array<String>) {
    val pebbleEngine = PebbleEngine.Builder().build()

    val applicationComponent = DaggerApplicationComponent.builder().build()

    server {
        +(map get "/" to com.felipecosta.microservice.app.helloworld.frontcontroller::HelloFrontCommand)
        +(map get "/json" to com.felipecosta.microservice.app.json.frontcontroller::JsonFrontCommand)
        +(map get "/notes" to com.felipecosta.microservice.app.notes.frontcontroller::NotesFrontCommand with PebbleRenderer(pebbleEngine))
        +(map get "/api/movies" to {
            MoviesFrontCommand().apply {
                val moviesComponent = DaggerMoviesComponent.builder().
                        applicationComponent(applicationComponent).
                        build()
                moviesComponent.inject(this)
            }
        })
    }

}
