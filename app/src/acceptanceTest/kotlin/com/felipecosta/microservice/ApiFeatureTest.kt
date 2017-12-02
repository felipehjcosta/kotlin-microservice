package com.felipecosta.microservice

import cucumber.api.java8.En
import kotlin.test.assertTrue

class ApiFeatureTest(apiClient: ApiClient) : En {

    private lateinit var movies: List<String>

    init {
        Given("""^the movie "(.*)" exists$""") { movieName: String ->
            apiClient.postMovie(movieName)
        }

        When("""^the user makes a request for movies$""") {
            movies = apiClient.getMovies()
        }

        Then("""^the movie "(.*)" are shown on response$""") { movieName: String ->
            assertTrue { movies.contains(movieName) }
        }

    }
}