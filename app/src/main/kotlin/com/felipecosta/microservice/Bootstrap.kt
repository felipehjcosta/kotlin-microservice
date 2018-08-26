package com.felipecosta.microservice

import com.felipecosta.microservice.app.core.data.RedisMoviesRepository
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.helloworld.frontcontroller.HelloFrontCommand
import com.felipecosta.microservice.app.json.frontcontroller.JsonFrontCommand
import com.felipecosta.microservice.app.movies.frontcontroller.*
import com.felipecosta.microservice.app.notes.frontcontroller.NotesFrontCommand
import com.felipecosta.microservice.server.*
import com.felipecosta.microservice.server.renderer.impl.PebbleRenderer
import com.github.salomonbrys.kodein.*
import com.mitchellbosecke.pebble.PebbleEngine

fun main(args: Array<String>) {
    val pebbleEngine = PebbleEngine.Builder().build()

    val kodein = Kodein {

        constant("redisUri") with (if (System.getenv("REDIS_URI") != null) System.getenv("REDIS_URI") else "redis:6379")

        bind<MoviesRepository>() with singleton { RedisMoviesRepository(instance("redisUri")) }

        bind<ListMoviesFrontCommand>() with provider { ListMoviesFrontCommand(instance()) }

        bind<GetMovieFrontCommand>() with provider { GetMovieFrontCommand(instance()) }

        bind<CreateMovieFrontCommand>() with provider { CreateMovieFrontCommand(instance()) }

        bind<UpdateMovieFrontCommand>() with provider { UpdateMovieFrontCommand(instance()) }

        bind<DeleteMovieFrontCommand>() with provider { DeleteMovieFrontCommand(instance()) }
    }

    server {

        handler { SparkServerHandler() }

        +(map get "/" to ::HelloFrontCommand)
        +(map get "/json" to ::JsonFrontCommand)
        +(map get "/notes" to ::NotesFrontCommand with PebbleRenderer(pebbleEngine))

        +(map get "/api/movies" to kodein.provider<ListMoviesFrontCommand>())

        +(map get "/api/movies/:id" to kodein.provider<GetMovieFrontCommand>())

        +(map post "/api/movies" to kodein.provider<CreateMovieFrontCommand>())

        +(map put "/api/movies/:id" to kodein.provider<UpdateMovieFrontCommand>())

        +(map delete "/api/movies/:id" to kodein.provider<DeleteMovieFrontCommand>())
    }

}
