package com.felipecosta.microservice.app.core.data

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import java.util.concurrent.atomic.AtomicInteger

class StubMovieRepository : MoviesRepository {

    private val movies =
            mutableMapOf(
                    (0 to "Awesome Marvel Movie").let { it.first to Movie(it.second, it.first) },
                    (1 to "X-Men").let { it.first to Movie(it.second, it.first) },
                    (2 to "Super-man").let { it.first to Movie(it.second, it.first) },
                    (3 to "The Avengers").let { it.first to Movie(it.second, it.first) }
            )

    private val lastId = AtomicInteger(movies.size - 1)

    override fun findAll(): List<Movie> = movies.values.toList()

    override fun find(id: Int) = movies[id]

    override fun save(movie: Movie): Movie {
        val id = lastId.incrementAndGet()
        val newMovie = movie.copy(id = id)
        movies[id] = newMovie
        return newMovie
    }

    override fun update(movie: Movie) {
        movies[movie.id] = movie
    }

    override fun delete(movie: Movie) {
        movies.remove(movie.id)
    }
}