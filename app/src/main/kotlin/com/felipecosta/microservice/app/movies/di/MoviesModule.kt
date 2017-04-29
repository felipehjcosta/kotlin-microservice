package com.felipecosta.microservice.app.movies.di

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.movies.domain.GetMoviesUseCase
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @MoviesScope
    @Provides
    fun providesGetMoviesUseCase(moviesRepository: MoviesRepository) = GetMoviesUseCase(moviesRepository)

}