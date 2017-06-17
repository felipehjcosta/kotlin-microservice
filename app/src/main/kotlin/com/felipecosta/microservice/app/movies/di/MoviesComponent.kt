package com.felipecosta.microservice.app.movies.di

import com.felipecosta.microservice.app.core.di.ApplicationComponent
import com.felipecosta.microservice.app.movies.frontcontroller.*
import dagger.Component

@MoviesScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MoviesModule::class))
interface MoviesComponent {
    fun inject(listMoviesFrontCommand: ListMoviesFrontCommand)

    fun inject(getMovieFrontCommand: GetMovieFrontCommand)

    fun inject(createMovieFrontCommand: CreateMovieFrontCommand)

    fun inject(updateMovieFrontCommand: UpdateMovieFrontCommand)

    fun inject(deleteMovieFrontCommand: DeleteMovieFrontCommand)
}