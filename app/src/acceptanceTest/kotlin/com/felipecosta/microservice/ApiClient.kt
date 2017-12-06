package com.felipecosta.microservice

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

class ApiClient(private val environment: Environment) {

    fun postMovie(movieName: String): JsonObject {
        val (_, _, result) = "${environment.baseUrl}api/movies".httpPost()
                .body("""{"name": "$movieName"}""")
                .responseString()

        return (Parser().parse(result.get().byteInputStream()) as JsonObject)["response"] as JsonObject
    }

    fun putMovie(id: Int, movieName: String): JsonObject {
        val (_, _, result) = "${environment.baseUrl}api/movies/$id".httpPut()
                .body("""{"name": "$movieName"}""")
                .responseString()

        return (Parser().parse(result.get().byteInputStream()) as JsonObject)["response"] as JsonObject
    }

    fun getMovies(): JsonArray<JsonObject> {
        val (_, _, result) = "${environment.baseUrl}api/movies".httpGet().responseString()

        return (Parser().parse(result.get().byteInputStream()) as JsonObject)["response"] as JsonArray<JsonObject>
    }

    fun deleteMovie(id: Int) {
        "${environment.baseUrl}api/movies/$id".httpDelete().responseString()
    }

}