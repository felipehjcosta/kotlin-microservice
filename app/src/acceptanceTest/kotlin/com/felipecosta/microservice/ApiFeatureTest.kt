package com.felipecosta.microservice

import cucumber.api.java8.En
import kotlin.test.assertTrue

class ApiFeatureTest(apiClient: ApiClient) : En {

    init {
        lateinit var movies: List<String>

        Given("""^the movie "(.*)" exists$""") { movieName: String ->
            apiClient.postMovie(movieName)
        }

        When("""^the user makes a request for movies$""") {
            movies = apiClient.getMovies()
        }

        Then("""^the movie "(.*)" are shown on response$""") { movieName: String ->
            assertTrue { movies.contains(movieName) }
        }


        var insertedMovieId = 0

        When("""^insert movie with title "(.*)"$""") { movieName: String ->
            insertedMovieId = apiClient.postMovie(movieName)
        }

        Then("""^the response should contains an id$""") {
            assertTrue { insertedMovieId > 0 }
        }
    }
}