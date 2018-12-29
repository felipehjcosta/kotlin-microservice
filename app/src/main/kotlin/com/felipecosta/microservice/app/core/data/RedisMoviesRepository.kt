package com.felipecosta.microservice.app.core.data

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import com.felipecosta.microservice.app.core.domain.entity.Movie
import io.lettuce.core.RedisClient
import io.lettuce.core.api.sync.RedisCommands

class RedisMoviesRepository(private val redisUri: String) : MoviesRepository {

    override fun findAll(): List<Movie> = executeOnRedis {
        keys("movie:*")
                .asSequence()
                .map { key -> hgetall(key) }
                .map { deserializeMovie(it) }
                .filterNotNull()
                .toList()
    }

    override fun find(id: Int): Movie? = executeOnRedis {
        deserializeMovie(hgetall("movie:$id"))
    }

    override fun save(movie: Movie): Movie = executeOnRedis {
        val keys = keys("movie:*")
        val lastIndex = if (keys.isEmpty()) 0 else keys.last().split(":").last().toInt().inc()
        val newMovie = movie.copy(id = lastIndex)
        hmset("movie:$lastIndex", serializeMovie(newMovie))
        newMovie
    }

    override fun update(movie: Movie) = executeOnRedis {
        val hasMovie = hgetall("movie:${movie.id}").isNotEmpty()
        if (hasMovie) {
            hmset("movie:${movie.id}", serializeMovie(movie))
        }
    }

    override fun delete(movie: Movie) {
        executeOnRedis {
            del("movie:${movie.id}")
        }
    }

    private fun deserializeMovie(rawMovie: Map<String, String>): Movie? {
        val rawName = rawMovie["name"]
        val rawId = rawMovie["id"]
        return if (rawName != null && rawId != null) {
            Movie(rawName, rawId.toInt())
        } else {
            null
        }
    }

    private fun serializeMovie(movie: Movie) = mapOf("name" to movie.name, "id" to "${movie.id}")

    private inline fun <reified T> executeOnRedis(body: RedisCommands<String, String>.() -> T): T {
        val client = RedisClient.create(redisUri)
        val connection = client.connect()

        val returnValue = body(connection.sync())

        connection.close()
        client.shutdown()

        return returnValue
    }
}
