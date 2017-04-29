package com.felipecosta.microservice.app.core.di

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(DataModule::class))
interface ApplicationComponent {
    fun moviesRepository(): MoviesRepository
}