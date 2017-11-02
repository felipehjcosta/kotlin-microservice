package com.felipecosta.microservice.app.core.di

import com.felipecosta.microservice.app.core.data.RedisMoviesRepository
import com.felipecosta.microservice.app.core.domain.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    companion object {
        const val redisUri = "h:p4d9bad74864deada66ebed2e832c6d6bf2de394afef54902351c836ae9850e0e@ec2-54-227-223-104.compute-1.amazonaws.com:60759"
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(): MoviesRepository = RedisMoviesRepository(redisUri)
}