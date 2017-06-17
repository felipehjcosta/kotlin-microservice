package com.felipecosta.microservice

import com.felipecosta.microservice.app.core.di.DaggerApplicationComponent
import com.felipecosta.microservice.app.helloworld.frontcontroller.HelloFrontCommand
import com.felipecosta.microservice.app.json.frontcontroller.JsonFrontCommand
import com.felipecosta.microservice.app.movies.di.DaggerMoviesComponent
import com.felipecosta.microservice.app.movies.frontcontroller.*
import com.felipecosta.microservice.app.notes.frontcontroller.NotesFrontCommand
import com.felipecosta.microservice.server.*
import com.felipecosta.microservice.server.renderer.impl.PebbleRenderer
import com.mitchellbosecke.pebble.PebbleEngine

fun main(args: Array<String>) {
    val pebbleEngine = PebbleEngine.Builder().build()

    val applicationComponent = DaggerApplicationComponent.builder().build()

    server {

        handler { SparkServerHandler() }

        +(map get "/" to ::HelloFrontCommand)
        +(map get "/json" to ::JsonFrontCommand)
        +(map get "/notes" to ::NotesFrontCommand with PebbleRenderer(pebbleEngine))


        +(map get "/api/movies" to {
            ListMoviesFrontCommand().apply {
                val moviesComponent = DaggerMoviesComponent.builder().
                        applicationComponent(applicationComponent).
                        build()
                moviesComponent.inject(this)
            }
        })

        +(map get "/api/movies/:id" to {
            GetMovieFrontCommand().apply {
                val moviesComponent = DaggerMoviesComponent.builder().
                        applicationComponent(applicationComponent).
                        build()
                moviesComponent.inject(this)
            }
        })

        +(map post "/api/movies" to {
            CreateMovieFrontCommand().apply {
                val moviesComponent = DaggerMoviesComponent.builder().
                        applicationComponent(applicationComponent).
                        build()
                moviesComponent.inject(this)
            }
        })

        +(map put "/api/movies/:id" to {
            UpdateMovieFrontCommand().apply {
                val moviesComponent = DaggerMoviesComponent.builder().
                        applicationComponent(applicationComponent).
                        build()
                moviesComponent.inject(this)
            }
        })

        +(map delete  "/api/movies/:id" to {
            DeleteMovieFrontCommand().apply {
                val moviesComponent = DaggerMoviesComponent.builder().
                        applicationComponent(applicationComponent).
                        build()
                moviesComponent.inject(this)
            }
        })
    }

}
