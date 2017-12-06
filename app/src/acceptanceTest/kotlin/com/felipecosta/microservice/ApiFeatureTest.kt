package com.felipecosta.microservice

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import cucumber.api.java8.En
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ApiFeatureTest(apiClient: ApiClient) : En {

    init {
        lateinit var postedMovie: JsonObject
        lateinit var moviesResponse: JsonArray<JsonObject>

        Given("""^the movie "(.*)" exists$""") { movieName: String ->
            postedMovie = apiClient.postMovie(movieName)
        }

        When("""^the user makes a request for movies$""") {
            moviesResponse = apiClient.getMovies()
        }

        Then("""^the movie "(.*)" are shown on response$""") { movieName: String ->
            assertTrue { moviesResponse.map { it["name"] }.contains(movieName) }
        }


        lateinit var insertedMovie: JsonObject

        When("""^insert movie with title "(.*)"$""") { movieName: String ->
            insertedMovie = apiClient.postMovie(movieName)
        }

        Then("""^the response should contains an id$""") {
            assertTrue { insertedMovie["id"] as Int > 0 }
        }


        lateinit var editedMovie: JsonObject

        When("""^the user changes this movie name to "(.*)"$""") { newMovieName: String ->
            editedMovie = apiClient.putMovie(postedMovie["id"] as Int, newMovieName)
        }

        Then("""^the movie name changed to "(.*)"$""") { editedMovieName: String ->
            assertEquals(editedMovieName, editedMovie["name"])
        }


        When("""^the user deletes it$""") {
            apiClient.deleteMovie(postedMovie["id"] as Int)
        }

        Then("""^the movie doesn't exist anymore$""") {
            assertFalse { apiClient.getMovies().map { it["id"] as Int }.contains(postedMovie["id"] as Int) }
        }


        lateinit var retrievedMovie: JsonObject

        When("""^the user retrieves it$""") {
            // Write code here that turns the phrase above into concrete actions
            retrievedMovie = apiClient.getMovie(postedMovie["id"] as Int)
        }

        Then("""^the movie are shown on the response$""") {
            // Write code here that turns the phrase above into concrete actions
            assertTrue { retrievedMovie.isNotEmpty() }
        }
    }
}