package com.felipecosta.microservice

import com.felipecosta.microservice.app.core.di.DaggerApplicationComponent
import com.felipecosta.microservice.app.movies.di.DaggerMoviesComponent
import com.felipecosta.microservice.app.movies.frontcontroller.MoviesFrontCommand
import com.felipecosta.microservice.server.*
import com.felipecosta.microservice.server.frontcontroller.impl.HelloSparkFrontCommand
import com.felipecosta.microservice.server.frontcontroller.impl.JsonSparkFrontCommand
import com.felipecosta.microservice.server.frontcontroller.impl.NotesFrontCommand
import com.felipecosta.microservice.server.renderer.impl.PebbleRenderer
import com.mitchellbosecke.pebble.PebbleEngine
import spark.servlet.SparkApplication

class BootstrapApplication : SparkApplication {
    override fun init() {
        val pebbleEngine = PebbleEngine.Builder().build()

        val applicationComponent = DaggerApplicationComponent.builder().build()

        server {
            +(map get "/" to ::HelloSparkFrontCommand)
            +(map get "/json" to ::JsonSparkFrontCommand)
            +(map get "/notes" to ::NotesFrontCommand with PebbleRenderer(pebbleEngine))
            +(map get "/api/movies" to {
                val moviesFrontCommand = MoviesFrontCommand()
                val moviesComponent = DaggerMoviesComponent.builder().
                        applicationComponent(applicationComponent).
                        build()
                moviesComponent.inject(moviesFrontCommand)
                moviesFrontCommand
            })
        }

    }
}
