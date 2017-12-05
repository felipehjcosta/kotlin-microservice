package com.felipecosta.microservice

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import cucumber.api.java8.En
import kotlin.test.assertTrue

class ApiFeatureTest(apiClient: ApiClient) : En {

    init {
        lateinit var moviesResponse: JsonArray<JsonObject>

        Given("""^the movie "(.*)" exists$""") { movieName: String ->
            apiClient.postMovie(movieName)
        }

        When("""^the user makes a request for movies$""") {
            moviesResponse = apiClient.getMovies()
        }

        Then("""^the movie "(.*)" are shown on response$""") { movieName: String ->
            assertTrue { moviesResponse.asSequence().map { it["name"] }.contains(movieName) }
        }

        lateinit var insertedMovie: JsonObject

        When("""^insert movie with title "(.*)"$""") { movieName: String ->
            insertedMovie = apiClient.postMovie(movieName)
        }

        Then("""^the response should contains an id$""") {
            assertTrue { insertedMovie["id"] as Int > 0 }
        }
    }
}