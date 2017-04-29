package com.felipecosta.microservice.app.core.di

import com.felipecosta.microservice.app.core.data.StubMovieRepository
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(): MoviesRepository = StubMovieRepository()
}